

# Resume Sorting System

A tool for parsing, scoring, and ranking job applicants based on configurable recruiter-defined criteria. Built with modern Java technologies and AI-powered resume mapping.

## **Technology Stack**

**Backend**

- Java 21
- Spring Boot 3.3.4
- Spring AI (OpenAI integration)
- Spring Data JPA with Hibernate
- Maven build system
- GraalVM native support

**Frontend**

- Vaadin 24 with custom themes
- Responsive design components

**Database**

- PostgreSQL with comprehensive schema
- JPA entity relationships
- Database migrations support

**Document Processing**

- Apache Tika for content extraction
- Multi-format document support


## **Prerequisites**

- Java 21 or higher
- Docker and Docker Compose
- PostgreSQL (or use provided Docker setup)
- OpenAI API key


## **Quick Start**

### **1. Clone the Repository**

```bash
git clone <your-repository-url>
cd resume-sorting-system
```


### **2. Environment Setup**

Create a `secret.properties` file in your project root:

```properties
# Database Configuration
DATABASE_URL=jdbc:postgresql://localhost:5433/resume
DB_USER=your_db_username
DB_PASSWORD=your_db_password

# OpenAI Configuration
OPENAI_API_KEY=your_openai_api_key

# Optional
BASE_URL=http://localhost:8080
PORT=8080
```


### **3. Start PostgreSQL Database**

Create a `docker-compose.yml` file:

```yaml
services:
  postgres:
    image: 'postgres:latest'
    environment:
      - 'POSTGRES_DB=resume'
      - POSTGRES_PASSWORD=${DB_PASSWORD}
      - POSTGRES_USER=${DB_USER}
    ports:
      - '5433:5432'
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
```

Start the database:

```bash
docker-compose up -d
```


### **4. Initialize Database Schema**

The application includes comprehensive database initialization. The schema will be created automatically on first run, including:

- Resume and candidate management tables
- Job vacancy tracking
- Scoring system tables
- Document storage
- Relationship mappings for all resume components


### **5. Build and Run**

```bash
# Build the application
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`

## **Docker Deployment**

### **Build and Run with Docker**

```bash
# Build the Docker image
docker build -t resume-sorting-system .

# Run the container
docker run -p 8080:8080 \
  -e DATABASE_URL=jdbc:postgresql://host.docker.internal:5433/resume \
  -e DB_USER=your_db_username \
  -e DB_PASSWORD=your_db_password \
  -e OPENAI_API_KEY=your_openai_api_key \
  resume-sorting-system
```


### **Complete Docker Compose Setup**

For a full production environment, update your `docker-compose.yml`:

```yaml
version: '3.8'
services:
  postgres:
    image: 'postgres:latest'
    environment:
      - 'POSTGRES_DB=resume'
      - 'POSTGRES_PASSWORD=${DB_PASSWORD}'
      - 'POSTGRES_USER=${DB_USER}'
    ports:
      - '5433:5432'
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - resume-network

  app:
    build: .
    ports:
      - '8080:8080'
    environment:
      - DATABASE_URL=jdbc:postgresql://postgres:5432/resume
      - DB_USER=${DB_USER}
      - DB_PASSWORD=${DB_PASSWORD}
      - OPENAI_API_KEY=${OPENAI_API_KEY}
    depends_on:
      - postgres
    networks:
      - resume-network

volumes:
  postgres_data:

networks:
  resume-network:
    driver: bridge
```

**Start the complete stack:**

```bash
docker-compose up --build
```


### **Environment Variables for Docker**

Create a `.env` file for Docker Compose:

```env
# Database Configuration
DB_USER=resume_user
DB_PASSWORD=secure_password

# OpenAI Configuration  
OPENAI_API_KEY=your_openai_api_key

# Optional Configuration
BASE_URL=http://localhost:8080
PORT=8080
```


## **AI Integration**

The system integrates with OpenAI's GPT-4o-mini model for intelligent resume parsing. Ensure your API key has sufficient credits and appropriate rate limits.

## **Future Enhancements**

- Security implementation (authentication/authorization)
- Enhanced front-end features
- Bulk resume processing
- Advanced analytics and reporting
- Machine learning model improvements

---

*This project is currently in active development. Feedback and contributions are welcome as we work towards a production-ready release.*

<div style="text-align: center">⁂</div>

[^1]: programming.java_development


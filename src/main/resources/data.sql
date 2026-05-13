-- ==========================================
-- 1. VACANCIES
-- ==========================================
INSERT INTO vacancy (id, vacancy_name, employment_type, work_mode, experience_level, status, location, salary_range, description, post_date)
VALUES
    (1, 'Full Stack Web Developer (React/Node.js)', 'FULL_TIME', 'HYBRID', 'MID_LEVEL', 'OPEN', 'New York, NY', '$95,000 - $130,000', 'Develop scalable web apps using React/Node.js. Optimize speed, write clean code, and collaborate on UI/UX.', '2026-05-01'),
    (2, 'Junior Java Backend Developer', 'FULL_TIME', 'REMOTE', 'JUNIOR', 'OPEN', 'Arnhem, Netherlands', '€45,000 - €55,000', 'Maintain and build robust APIs using Java and Spring Boot. Strong understanding of SQL and Git required.', '2026-05-10'),
    (3, 'DevOps Engineer', 'CONTRACT', 'ONSITE', 'SENIOR', 'OPEN', 'London, UK', '£80,000 - £100,000', 'Manage Linux servers, CI/CD pipelines, and ensure network security.', '2026-05-12');

SELECT setval('vacancy_seq', (SELECT MAX(id) FROM vacancy));

-- ==========================================
-- 2. RESUMES
-- ==========================================
-- Work Permit & Visa set to NULL instead of false, as they are unstated unless explicitly required.
INSERT INTO resume (id, vacancy_id, first_name, last_name, email, gender, date_of_birth, portfolio_url, drivers_license, citizenship, telephone, address, work_permit_required, visa_required)
VALUES
-- Vacancy 1: Full Stack (IDs 1-10)
(1, 1, 'Jordan', 'Belfort', 'j.belfort@email.com', NULL, NULL, 'https://jordanb.dev', NULL, 'US', '555-0101', 'Manhattan, NY', NULL, NULL),
(2, 1, 'Taylor', 'Reed', 'treed.codes@email.com', NULL, NULL, 'https://github.com/treed', NULL, 'US', '555-0102', 'Brooklyn, NY', NULL, NULL),
(3, 1, 'Morgan', 'Chen', 'mchen.dev@email.com', NULL, NULL, 'https://mchen.io', NULL, 'CA', '555-0103', 'Queens, NY', true, NULL),
(4, 1, 'Alex', 'Skyler', 'alex.sky@email.com', NULL, NULL, NULL, NULL, 'US', '555-0104', 'Jersey City, NJ', NULL, NULL),
(5, 1, 'Casey', 'Emerson', 'casey.e@email.com', NULL, NULL, 'https://casey.tech', NULL, 'US', '555-0105', 'New York, NY', NULL, NULL),
(6, 1, 'Pat', 'Higgins', 'phiggins@email.com', NULL, NULL, NULL, NULL, 'IE', '555-0106', 'New York, NY', true, true),
(7, 1, 'Riley', 'Quinn', 'rquinn@email.com', NULL, NULL, 'https://rileyq.me', NULL, 'US', '555-0107', 'Staten Island, NY', NULL, NULL),
(8, 1, 'Jamie', 'Vance', 'jvance.eng@email.com', NULL, NULL, 'https://vance.builds', NULL, 'US', '555-0108', 'Bronx, NY', NULL, NULL),
(9, 1, 'Sasha', 'Lee', 'slee_dev@email.com', NULL, NULL, NULL, NULL, 'US', '555-0109', 'Manhattan, NY', NULL, NULL),
(10, 1, 'Drew', 'Marlowe', 'drew.m@email.com', NULL, NULL, 'https://drewcodes.com', NULL, 'US', '555-0110', 'Brooklyn, NY', NULL, NULL),

-- Vacancy 2: Junior Java (IDs 11-20)
(11, 2, 'Lars', 'Janssen', 'l.janssen@email.nl', NULL, NULL, 'https://github.com/larsj-dev', NULL, 'NL', '31-61234567', 'Arnhem, NL', NULL, NULL),
(12, 2, 'Priya', 'Patel', 'ppatel.java@email.com', NULL, NULL, NULL, NULL, 'IN', '31-62233445', 'Nijmegen, NL', true, true),
(13, 2, 'Bram', 'De Wit', 'bram.dewit@email.nl', NULL, NULL, 'https://bram.codes', NULL, 'NL', '31-63344556', 'Utrecht, NL', NULL, NULL),
(14, 2, 'Elena', 'Ivanova', 'e.ivanova@email.com', NULL, NULL, NULL, NULL, 'BG', '31-64455667', 'Arnhem, NL', NULL, NULL),
(15, 2, 'Sven', 'Bakker', 's.bakker99@email.nl', NULL, NULL, 'https://svenbakker.io', NULL, 'NL', '31-65566778', 'Wageningen, NL', NULL, NULL),
(16, 2, 'Li', 'Wei', 'li.wei.dev@email.com', NULL, NULL, NULL, NULL, 'CN', '31-66677889', 'Enschede, NL', true, true),
(17, 2, 'Anke', 'Vermeulen', 'a.vermeulen@email.nl', NULL, NULL, 'https://github.com/anke-v', NULL, 'NL', '31-67788990', 'Apeldoorn, NL', NULL, NULL),
(18, 2, 'Marco', 'Rossi', 'm.rossi@email.it', NULL, NULL, NULL, NULL, 'IT', '31-68899001', 'Arnhem, NL', NULL, NULL),
(19, 2, 'Tess', 'Hendriks', 'tess.h@email.nl', NULL, NULL, 'https://tessjava.dev', NULL, 'NL', '31-69900112', 'Zutphen, NL', NULL, NULL),
(20, 2, 'Omar', 'Hassan', 'o.hassan@email.com', NULL, NULL, NULL, NULL, 'EG', '31-60011223', 'Arnhem, NL', true, NULL),

-- Vacancy 3: DevOps (IDs 21-30)
(21, 3, 'Alex', 'Sterling', 'a.sterling.ops@email.co.uk', NULL, NULL, 'https://sterling-infra.io', NULL, 'UK', '44-7700-9001', 'London, UK', NULL, NULL),
(22, 3, 'Sam', 'Kowalski', 'skowalski@email.com', NULL, NULL, NULL, NULL, 'PL', '44-7700-9002', 'London, UK', NULL, NULL),
(23, 3, 'Jordan', 'Vance', 'j.vance.cloud@email.com', NULL, NULL, 'https://github.com/jv-ops', NULL, 'US', '44-7700-9003', 'London, UK', true, true),
(24, 3, 'Charlie', 'Morgan', 'c.morgan.devops@email.co.uk', NULL, NULL, NULL, NULL, 'UK', '44-7700-9004', 'Reading, UK', NULL, NULL),
(25, 3, 'Robin', 'Lee', 'rlee.sre@email.com', NULL, NULL, 'https://rlee.tech', NULL, 'UK', '44-7700-9005', 'London, UK', NULL, NULL),
(26, 3, 'Casey', 'Higgins', 'chiggins.infra@email.com', NULL, NULL, NULL, NULL, 'IE', '44-7700-9006', 'London, UK', NULL, NULL),
(27, 3, 'Taylor', 'Swift', 't.swift.ops@email.co.uk', NULL, NULL, NULL, NULL, 'UK', '44-7700-9007', 'Croydon, UK', NULL, NULL),
(28, 3, 'Avery', 'Gomez', 'avery.g@email.com', NULL, NULL, 'https://avery-cloud.dev', NULL, 'ES', '44-7700-9008', 'London, UK', NULL, NULL),
(29, 3, 'Morgan', 'Black', 'mblack.linux@email.co.uk', NULL, NULL, NULL, NULL, 'UK', '44-7700-9009', 'London, UK', NULL, NULL),
(30, 3, 'Riley', 'Parker', 'r.parker@email.com', NULL, NULL, 'https://parker-infra.me', NULL, 'AU', '44-7700-9010', 'London, UK', true, true);

SELECT setval('resume_seq', (SELECT MAX(id) FROM resume));

-- ==========================================
-- 3. WORK EXPERIENCE
-- ==========================================
INSERT INTO work_experience (company_name, job_title, description, start_date, end_date, resume_id)
VALUES
-- Resume 1 (Mid Full Stack - ~7 years exp)
('StartUp Inc', 'Junior Developer', 'Developed responsive UI components in React and managed application state using Redux.', '2019-06-01', '2021-02-15', 1),
('FinTech Solutions', 'Software Engineer II', 'Led the migration of a legacy PHP monolith to a React/Node microservices architecture.', '2021-03-01', NULL, 1),

-- Resume 2 (Mid Full Stack - ~7 years exp)
('Web Agency', 'Junior Web Developer', 'Built custom frontend components and transitioned clients to headless CMS setups.', '2019-01-15', '2021-10-01', 2),
('Creative Digital', 'Frontend Engineer', 'Implemented complex animations, Next.js routing, and performance optimizations for high-traffic e-commerce sites.', '2021-11-01', NULL, 2),

-- Resume 3 (Mid Full Stack - ~6.5 years exp)
('University IT', 'Student Web Developer', 'Maintained university internal portals using vanilla JS, PHP, and PostgreSQL.', '2018-09-01', '2019-05-01', 3),
('Maple Leaf Tech', 'Junior Developer', 'Assisted in building RESTful APIs using Node/Express.', '2019-06-01', '2022-03-15', 3),
('Maple Leaf Tech', 'Full Stack Developer', 'Promoted to handle end-to-end feature delivery and manage MongoDB database scaling.', '2022-04-01', NULL, 3),

-- Resume 4 (Mid Full Stack - ~6 years exp post-bootcamp)
('Local Non-Profit', 'Web Developer Intern', 'Assisted in updating WordPress themes and writing custom JavaScript widgets.', '2020-05-01', '2020-12-01', 4),
('E-Comm Builders', 'Frontend Engineer', 'Developed cart checkout flows and integrated third-party payment gateways in React.', '2021-01-01', '2023-05-01', 4),
('Tech Solutions LLC', 'Full Stack Developer', 'Architected Stripe API integrations and built responsive admin dashboards in Next.js.', '2023-06-01', NULL, 4),

-- Resume 5 (Mid Full Stack - ~7.5 years exp)
('DataStream', 'Junior Backend Developer', 'Wrote Express.js endpoints for high-throughput data ingestion and implemented basic Redis caching.', '2018-08-01', '2021-07-01', 5),
('Global Tech', 'Software Developer', 'Contributed to a cloud-based real-time analytics dashboard utilized by over 50,000 daily users.', '2021-08-01', NULL, 5),

-- Resume 6 (Mid Full Stack - ~4.5 years exp)
('Dublin Devs', 'Junior Software Engineer', 'Worked on internal CRM tools using Vue.js and Firebase.', '2021-07-01', '2022-12-31', 6),
('TechCorp', 'Full Stack Developer', 'Built a React-Native mobile application and designed its corresponding Node.js backend infrastructure.', '2023-01-15', NULL, 6),

-- Resume 7 (Mid Full Stack - ~5.5 years exp)
('AdAgency NY', 'Interactive Developer', 'Created high-conversion landing pages using React, Tailwind CSS, and Figma mockups.', '2020-09-01', '2023-08-01', 7),
('Marketing Hub', 'Web Developer', 'Managed headless CMS integrations using Next.js and optimized core web vitals across client sites.', '2023-09-01', NULL, 7),

-- Resume 8 (Mid Full Stack - ~6.5 years exp)
('SaaS Factory', 'Backend Specialist', 'Optimized complex PostgreSQL query execution plans, reducing average database latency by 40%.', '2019-07-01', '2022-08-01', 8),
('Cloud Services Inc', 'Software Engineer', 'Maintained core REST APIs and transitioned legacy endpoints to GraphQL for mobile clients.', '2022-09-01', NULL, 8),

-- Resume 9 (Mid Full Stack - ~5 years exp)
('B2B Tech', 'Junior Web Developer', 'Integrated SendGrid APIs for automated email workflows and maintained Docker containers for local dev.', '2021-08-01', '2024-01-15', 9),
('FinServe', 'Full Stack Developer', 'Built secure, customer-facing financial portals utilizing React, Node.js, and strict compliance standards.', '2024-02-01', NULL, 9),

-- Resume 10 (Mid Full Stack - ~5.5 years exp)
('NextGen Systems', 'Junior Engineer', 'Spearheaded the migration of legacy JavaScript codebases to strict TypeScript across 3 frontend projects.', '2020-07-01', '2023-03-01', 10),
('Innovate UI', 'Full Stack Engineer', 'Architected a multi-tenant SaaS application backend utilizing AWS serverless infrastructure.', '2023-04-01', NULL, 10),

-- Resume 11 (Junior Java - Career Switcher)
('Arnhem Plaza Hotel', 'Hospitality Manager', 'Managed daily hotel operations, staff scheduling, and customer relations for over a decade before transitioning into software engineering.', '2012-05-01', '2023-08-01', 11),
('Dutch-IT Solutions', 'Junior Developer Intern', 'Assisted in building REST APIs using Spring Boot 3.0 and wrote unit tests covering 80% of new endpoints.', '2025-02-01', '2025-08-01', 11),
('Dutch-IT Solutions', 'Junior Java Developer', 'Hired full-time post-graduation. Developing microservices for logistics clients.', '2025-09-01', NULL, 11),

-- Resume 12 (Junior Java - ~1.5 years exp)
('Infosys', 'System Engineer Trainee', 'Maintained enterprise Java applications, executed regression testing, and resolved production bugs.', '2024-06-01', '2025-08-01', 12),
('StartUp India', 'Junior Backend Dev', 'Developing containerized microservices using Java 17 and Docker for a fintech startup.', '2025-09-01', NULL, 12),

-- Resume 13 (Junior Java - ~2 years exp)
('TU Delft Labs', 'Research Assistant', 'Wrote Python and Java scripts for large-scale data parsing and scientific modeling.', '2022-01-01', '2023-06-01', 13),
('HealthTech NL', 'Junior Backend Developer', 'Developed secure backend microservices for patient record systems utilizing Spring Cloud and Hibernate.', '2024-01-15', NULL, 13),

-- Resume 14 (Junior Java - ~3 years mixed exp)
('Tech Support BG', 'Customer Support Tech', 'Troubleshot complex SaaS software issues for enterprise clients, identifying backend bugs from logs.', '2022-08-01', '2023-10-01', 14),
('Sofia Software', 'Junior Java Developer', 'Implemented bug fixes, refactored legacy code, and improved unit test coverage in a monolithic Java application.', '2024-01-01', NULL, 14),

-- Resume 15 (Junior Java - ~2 years exp)
('AgriData', 'Software Intern', 'Automated data ingestion tasks for smart agricultural sensors using Java and Apache Kafka.', '2024-03-01', '2024-09-01', 15),
('AgriData', 'Junior Java Developer', 'Promoted from intern. Currently maintaining backend services and optimizing database read operations.', '2024-10-01', NULL, 15),

-- Resume 16 (Junior Java - ~2.5 years exp)
('Enschede Tech', 'QA Tester', 'Wrote and maintained automated testing suites using Selenium, JUnit, and Java.', '2023-08-01', '2025-01-15', 16),
('Logistics BV', 'Junior Developer', 'Building internal tracking APIs using Spring Boot and securing endpoints with Spring Security.', '2025-02-01', NULL, 16),

-- Resume 17 (Junior Java - ~2 years exp)
('Radboud University', 'Teaching Assistant', 'Assisted professors in grading Java assignments and tutoring first-year computing science students.', '2023-02-01', '2024-06-01', 17),
('WebShop BV', 'Junior Java Dev', 'Refactored legacy e-commerce monolith to Spring Boot microservices. Improved raw MySQL query performance.', '2024-09-01', NULL, 17),

-- Resume 18 (Junior Java - ~2.5 years exp)
('Freelance', 'Web Developer', 'Built simple websites and backend logic using PHP and basic Java applets for local Italian businesses.', '2023-01-01', '2024-05-01', 18),
('Milan Tech', 'Junior Backend Engineer', 'Developing RESTful endpoints utilizing Java 17 and Spring Data JPA for a regional banking application.', '2024-06-01', NULL, 18),

-- Resume 19 (Junior Java - ~1 year exp)
('Finans NL', 'Trainee Developer', 'Shadowed senior engineers, learning OAuth2 security protocols and Kotlin basics.', '2025-07-01', '2025-12-01', 19),
('Finans NL', 'Junior Java Developer', 'Maintaining daily transaction reporting modules and fixing level-2 production bugs.', '2026-01-01', NULL, 19),

-- Resume 20 (Junior Java - ~2.5 years exp)
('Cairo IT Services', 'Software Intern', 'Learned version control (Git), agile methodologies, and basic Java enterprise application architecture.', '2023-06-01', '2023-09-01', 20),
('Global Soft', 'Junior Software Engineer', 'Participating in bi-weekly agile sprints to deliver Spring Boot features and database migrations.', '2024-01-01', NULL, 20),

-- Resume 21 (Senior DevOps - ~14 years exp)
('Local Host Co', 'Linux Sysadmin', 'Managed Apache web servers, handled basic Bash scripting, and maintained server uptime for 50+ clients.', '2012-07-01', '2015-08-15', 21),
('DataSecure', 'Infrastructure Engineer', 'Managed a fleet of 200+ Linux servers and implemented SOC2 compliance monitoring tools.', '2015-09-01', '2021-04-30', 21),
('CloudScale UK', 'Senior DevOps Engineer', 'Architected completely automated AWS multi-region infrastructure provisioning using Terraform and Ansible.', '2021-05-01', NULL, 21),

-- Resume 22 (Senior DevOps - ~12 years exp)
('Warsaw Telecom', 'Network Engineer', 'Configured enterprise Cisco routers, managed VPN infrastructure, and monitored network bandwidth.', '2014-07-01', '2017-06-01', 22),
('EuroBank', 'SysAdmin', 'Automated virtual machine deployments and routine maintenance tasks using Ansible playbooks.', '2017-07-01', '2019-12-31', 22),
('FinTech Global', 'Senior SRE', 'Lead on-call rotation for high-frequency trading platforms. Managed complex Kubernetes clusters via Prometheus/Grafana.', '2020-01-01', NULL, 22),

-- Resume 23 (Senior DevOps - ~10.5 years exp)
('Bay Area Tech', 'Software Engineer', 'Wrote Python backend services and self-managed deployments using basic Docker containers.', '2015-07-01', '2019-02-15', 23),
('Silicon Cloud', 'DevOps Engineer', 'Automated container orchestration using AWS EKS, managed deployment strategies using Helm charts.', '2019-03-01', '2023-12-01', 23),
('Global Infra', 'Lead Cloud Engineer', 'Leading a team of 4 engineers to migrate legacy on-prem enterprise services to a fully cloud-native AWS setup.', '2024-01-01', NULL, 23),

-- Resume 24 (Senior DevOps - ~15 years exp)
('IT Support UK', 'Helpdesk Level 2', 'Acted as the primary escalation point for severe internal server and networking issues.', '2010-08-01', '2013-12-31', 24),
('Hosting BV', 'Systems Administrator', 'Managed high-availability VMware vSphere clusters and enterprise SAN storage systems.', '2014-01-01', '2018-12-31', 24),
('Enterprise Corp', 'Senior DevOps Specialist', 'Implemented robust CI/CD pipelines from scratch using Jenkins, GitLab CI, and custom Bash scripting.', '2019-01-01', NULL, 24),

-- Resume 25 (Senior DevOps - ~12.5 years exp)
('Media House', 'Junior Sysadmin', 'Maintained render farm hardware, applied OS patches, and managed local networking.', '2013-10-01', '2016-08-01', 25),
('Creative Cloud', 'Infrastructure Engineer', 'Designed and built highly resilient, scalable storage clusters utilizing Ceph.', '2016-09-01', '2018-10-31', 25),
('London Media', 'Lead Infrastructure Engineer', 'Built, scaled, and secured a massive Proxmox private cloud environment supporting 500+ internal users.', '2018-11-01', NULL, 25),

-- Resume 26 (Senior DevOps - ~11.5 years exp)
('Dublin Bank', 'IT Support Analyst', 'Provided internal application support, managed Active Directory, and handled domain policy configuration.', '2014-07-01', '2017-05-15', 26),
('Tech Solutions IE', 'Systems Engineer', 'Planned and executed the migration of 300+ legacy Virtual Machines to Azure cloud infrastructure.', '2017-06-01', '2021-08-31', 26),
('FinServe UK', 'Senior DevOps Engineer', 'Implemented strict GitOps workflows leveraging ArgoCD and Kubernetes for zero-downtime deployments.', '2021-09-01', NULL, 26),

-- Resume 27 (Senior DevOps - ~14.5 years exp)
('GovTech UK', 'Systems Administrator', 'Managed and hardened RedHat enterprise Linux servers within a highly secure, air-gapped public sector environment.', '2011-09-01', '2016-03-31', 27),
('Cloud Consult', 'Cloud Architect', 'Designed scalable, fault-tolerant AWS architectures specifically tailored for UK public sector clients.', '2016-04-01', '2022-11-30', 27),
('Security First', 'DevSecOps Lead', 'Integrated automated vulnerability scanning and strict compliance checks directly into standard CI/CD pipelines.', '2022-12-01', NULL, 27),

-- Resume 28 (Senior DevOps - ~13 years exp)
('Madrid Systems', 'Junior Developer', 'Wrote PHP web applications and handled manual deployment processes via FTP scripts.', '2013-01-01', '2016-05-31', 28),
('Madrid Systems', 'Systems Administrator', 'Transitioned the company from manual server updates to fully Docker-based, reproducible deployments.', '2016-06-01', '2019-05-31', 28),
('Madrid Systems', 'DevOps Specialist', 'Migrated legacy, fragile Jenkins pipelines to a modernized, template-driven GitHub Actions workflow.', '2019-06-01', '2024-02-28', 28),
('UK Retail', 'Platform Engineer', 'Currently managing high-traffic e-commerce Kubernetes clusters experiencing immense holiday load spikes.', '2024-03-01', NULL, 28),

-- Resume 29 (Senior DevOps - ~10.5 years exp)
('Startup Network', 'Network Admin', 'Managed physical office networks, configured firewalls, and administered internal DNS/DHCP servers.', '2015-08-01', '2018-07-31', 29),
('TechHub London', 'Infrastructure Engineer', 'Automated all new cloud server provisioning entirely through Terraform state modules.', '2018-08-01', '2022-04-30', 29),
('Data Corp', 'Senior Site Reliability Engineer', 'Reduced overall system downtime by 99% by implementing aggressive Prometheus alerting and auto-scaling logic.', '2022-05-01', NULL, 29),

-- Resume 30 (Senior DevOps - ~16 years exp)
('Sydney IT', 'Systems Engineer', 'Administered complex Windows Server environments, Active Directory forests, and Hyper-V virtualization clusters.', '2010-02-01', '2015-12-31', 30),
('Oz Cloud', 'Senior Consultant', 'Consulted for Fortune 500 enterprise clients, directing massive on-premise to Azure cloud migration projects.', '2016-01-01', '2025-05-31', 30),
('Global Finance', 'Principal DevOps Engineer', 'Currently leading the entire platform engineering organization in London, defining cloud strategy and security protocols.', '2025-06-01', NULL, 30);

SELECT setval('work_experience_seq', (SELECT MAX(id) FROM work_experience));

-- ==========================================
-- 4. EDUCATION (Masters now include their Bachelors)
-- ==========================================
INSERT INTO education (id, institution_name, degree_type, major_subject, enrollment_date, graduation_date, resume_id)
VALUES
-- Bachelors for those without Masters
(1, 'NYU', 'Bachelor', 'Computer Science', '2015-09-01', '2019-05-20', 1),
(2, 'Parsons School of Design', 'Bachelor', 'Digital Media', '2014-09-01', '2018-06-01', 2),
(3, 'University of Toronto', 'Bachelor', 'Software Engineering', '2015-09-01', '2019-05-15', 3),
(4, 'Fullstack Academy', 'Bootcamp', 'Software Engineering', '2019-10-01', '2020-04-01', 4),
(6, 'Trinity College Dublin', 'Bachelor', 'Computer Science', '2017-09-01', '2021-05-01', 6),
(7, 'Pratt Institute', 'Bachelor', 'Interaction Design', '2016-09-01', '2020-05-01', 7),
(9, 'State University', 'Bachelor', 'Information Technology', '2017-09-01', '2021-05-01', 9),
(10, 'MIT', 'Bachelor', 'Computer Science', '2016-09-01', '2020-06-01', 10),
(11, 'Hogeschool van Arnhem en Nijmegen', 'Bachelor', 'Informatics', '2021-09-01', '2025-07-15', 11),
(12, 'University of Delhi', 'Bachelor', 'Computer Science', '2020-08-01', '2024-05-30', 12),
(14, 'Sofia University', 'Bachelor', 'Software Engineering', '2018-09-01', '2022-06-01', 14),
(15, 'University of Twente', 'Bachelor', 'Business & IT', '2020-09-01', '2024-07-01', 15),
(16, 'Tsinghua University', 'Bachelor', 'Computer Science', '2019-09-01', '2023-07-01', 16),
(17, 'Radboud University', 'Bachelor', 'Computing Science', '2020-09-01', '2024-08-01', 17),
(19, 'Vrije Universiteit Amsterdam', 'Bachelor', 'Computer Science', '2021-09-01', '2025-06-01', 19),
(20, 'Cairo University', 'Bachelor', 'Information Systems', '2019-09-01', '2023-05-01', 20),
(21, 'University of Manchester', 'Bachelor', 'Computer Science', '2008-09-01', '2012-06-01', 21),
(23, 'Stanford University', 'Bachelor', 'Electrical Engineering', '2011-09-01', '2015-05-01', 23),
(24, 'University of Leeds', 'Bachelor', 'IT Management', '2006-09-01', '2010-06-01', 24),
(26, 'University College Dublin', 'Bachelor', 'Computer Science', '2010-09-01', '2014-06-01', 26),
(27, 'King''s College London', 'Bachelor', 'Computer Science', '2007-09-01', '2011-06-01', 27),
(28, 'Universidad Politécnica de Madrid', 'Bachelor', 'Software Eng', '2008-09-01', '2012-06-01', 28),
(29, 'University of Edinburgh', 'Bachelor', 'Computer Science', '2011-09-01', '2015-06-01', 29),
(30, 'University of Sydney', 'Bachelor', 'Information Technology', '2006-03-01', '2009-11-01', 30),

-- Master's Holders + Their Backfilled Bachelors
(5, 'Columbia University', 'Master', 'Computer Science', '2016-09-01', '2018-05-01', 5),
(31, 'New York University', 'Bachelor', 'Computer Science', '2012-09-01', '2016-05-15', 5),

(8, 'Georgia Tech', 'Master', 'Computer Science', '2017-08-01', '2019-05-01', 8),
(32, 'University of Georgia', 'Bachelor', 'Software Engineering', '2013-08-01', '2017-05-15', 8),

(13, 'TU Delft', 'Master', 'Computer Science', '2021-09-01', '2023-06-20', 13),
(33, 'Eindhoven University of Technology', 'Bachelor', 'Computer Science', '2018-09-01', '2021-07-01', 13),

(18, 'Politecnico di Milano', 'Master', 'Software Engineering', '2021-09-01', '2023-12-15', 18),
(34, 'University of Milan', 'Bachelor', 'Informatics', '2018-09-01', '2021-07-15', 18),

(22, 'Warsaw University of Technology', 'Master', 'Telecommunications', '2012-10-01', '2014-06-01', 22),
(35, 'Warsaw University of Technology', 'Bachelor', 'Computer Science', '2009-10-01', '2012-06-01', 22),

(25, 'Imperial College London', 'Master', 'Cyber Security', '2012-09-01', '2013-09-01', 25),
(36, 'University College London', 'Bachelor', 'Computer Science', '2009-09-01', '2012-06-01', 25);

SELECT setval('education_seq', (SELECT MAX(id) FROM education));

-- ==========================================
-- 5. PROJECTS
-- ==========================================
INSERT INTO project (id, resume_id, project_name, description)
VALUES
    (1, 1, 'E-Commerce React Template', 'Built a reusable React/Redux e-commerce frontend mapped to dummy APIs.'),
    (2, 2, 'Personal Crypto Dashboard', 'Next.js dashboard integrating Binance APIs to track DCA strategy and portfolio allocation.'),
    (3, 11, 'Smart DCA Bot', 'Java-based automation running on a Raspberry Pi using Kraken Pro APIs for scheduled buys.'),
    (4, 13, 'Homelab Media Server', 'Managed Nextcloud, Jellyfin, and Immich instances orchestrated via Docker Compose on Ubuntu Server.'),
    (5, 17, 'Obsidian Sync Plugin', 'Custom sync plugin written in TypeScript to backup Obsidian vaults to a self-hosted WebDAV server.'),
    (6, 21, 'Terraform AWS Modules', 'Open-source contributor to common AWS Terraform modules used for standing up secure VPCs.'),
    (7, 25, 'Proxmox Homelab Scripts', 'Collection of Bash and Python scripts to automate LXC container deployment in Proxmox.'),
    (8, 28, 'K8s GitOps Boilerplate', 'ArgoCD and Kubernetes boilerplate repository for quick-starting microservice deployments.');

SELECT setval('project_seq', (SELECT MAX(id) FROM project));

-- ==========================================
-- 6. VOLUNTEER WORK
-- ==========================================
INSERT INTO volunteer (id, resume_id, organization_name, role, description, start_date, end_date)
VALUES
    (1, 4, 'Code for America', 'Volunteer Developer', 'Helped build accessible web forms for local government assistance programs using React.', '2021-02-01', '2022-02-01'),
    (2, 6, 'CoderDojo Dublin', 'Coding Mentor', 'Taught basic Python and HTML/CSS to children ages 7-14 in weekend workshops.', '2022-09-01', '2023-12-01'),
    (3, 11, 'Arnhem Tech Meetup', 'Co-Organizer', 'Organize monthly meetups for local junior developers, booking speakers and arranging venues.', '2025-05-01', NULL),
    (4, 15, 'University IT Helpdesk', 'Student Volunteer', 'Assisted incoming freshmen with campus Wi-Fi setup and software installation during orientation week.', '2021-08-01', '2021-09-01'),
    (5, 23, 'Linux Foundation', 'Documentation Contributor', 'Wrote and translated community documentation regarding Kubernetes networking ingress controllers.', '2021-01-01', '2022-12-01'),
    (6, 29, 'London Open Source Network', 'Infrastructure Volunteer', 'Maintained the community forum and mailing list servers hosted on DigitalOcean.', '2019-05-01', '2023-05-01');

SELECT setval('volunteer_seq', (SELECT MAX(id) FROM volunteer));
-- ==========================================
-- 8. CERTIFICATIONS
-- ==========================================
INSERT INTO certification (id, certification_name, description, date, resume_id)
VALUES
    (1, 'AWS Certified Developer - Associate', 'Validates core AWS services knowledge and basic AWS architecture best practices.', '2022-04-15', 1),
    (2, 'MongoDB Associate Developer', 'Certification demonstrating knowledge in data modeling, querying, and indexing in MongoDB.', '2023-01-20', 5),
    (3, 'PostgreSQL Associate Certification', 'Covers fundamental concepts of PostgreSQL database management and complex querying.', '2021-11-10', 8),
    (4, 'Oracle Certified Associate, Java SE 8 Programmer', 'Fundamental Java programming concepts, object-oriented principles, and basic APIs.', '2024-12-05', 11),
    (5, 'Spring Professional Certification', 'Comprehensive knowledge of the Spring Framework and Spring Boot for building enterprise Java applications.', '2025-06-15', 15),
    (6, 'AWS Certified Solutions Architect - Professional', 'Advanced certification for designing highly scalable, highly available, and fault-tolerant applications on AWS.', '2023-08-10', 21),
    (7, 'CKA: Certified Kubernetes Administrator', 'Validates skills, knowledge, and competency to perform the responsibilities of Kubernetes administrators.', '2021-05-22', 23),
    (8, 'CISSP', 'Certified Information Systems Security Professional - validation of deep technical and managerial competence in cybersecurity.', '2020-09-15', 27),
    (9, 'Microsoft Certified: Azure Solutions Architect Expert', 'Validates expertise in designing cloud and hybrid solutions that run on Microsoft Azure.', '2022-11-30', 30);

SELECT setval('certification_seq', (SELECT MAX(id) FROM certification));

-- ==========================================
-- 9. RESUME SKILLS
-- ==========================================
INSERT INTO resume_skills (id, skill, resume_id)
VALUES
    -- Full Stack React/Node.js (Resumes 1-10)
    (1, 'React', 1), (2, 'Node.js', 1), (3, 'Redux', 1), (4, 'PostgreSQL', 1),
    (5, 'React', 2), (6, 'Next.js', 2), (7, 'Tailwind CSS', 2),
    (8, 'JavaScript', 3), (9, 'Node.js', 3), (10, 'MongoDB', 3), (11, 'Express.js', 3),
    (12, 'TypeScript', 4), (13, 'React', 4), (14, 'Next.js', 4), (15, 'Stripe API', 4),
    (16, 'Node.js', 5), (17, 'Express.js', 5), (18, 'Redis', 5),
    (19, 'Vue.js', 6), (20, 'Firebase', 6), (21, 'React-Native', 6), (22, 'Node.js', 6),
    (23, 'React', 7), (24, 'Tailwind CSS', 7), (25, 'Figma', 7), (26, 'Next.js', 7),
    (27, 'PostgreSQL', 8), (28, 'Node.js', 8), (29, 'GraphQL', 8),
    (30, 'React', 9), (31, 'Node.js', 9), (32, 'Docker', 9),
    (33, 'TypeScript', 10), (34, 'AWS', 10), (35, 'React', 10), (36, 'Serverless', 10),

    -- Junior Java (Resumes 11-20)
    (37, 'Java', 11), (38, 'Spring Boot', 11), (39, 'REST APIs', 11), (40, 'JUnit', 11),
    (41, 'Java 17', 12), (42, 'Docker', 12), (43, 'SQL', 12),
    (44, 'Java', 13), (45, 'Python', 13), (46, 'Spring Cloud', 13), (47, 'Hibernate', 13),
    (48, 'Java', 14), (49, 'Git', 14), (50, 'Debugging', 14),
    (51, 'Java', 15), (52, 'Apache Kafka', 15), (53, 'SQL', 15),
    (54, 'Java', 16), (55, 'Spring Security', 16), (56, 'Selenium', 16), (57, 'JUnit', 16),
    (58, 'Java', 17), (59, 'Spring Boot', 17), (60, 'MySQL', 17),
    (61, 'Java 17', 18), (62, 'Spring Data JPA', 18), (63, 'PHP', 18),
    (64, 'Java', 19), (65, 'Kotlin', 19), (66, 'OAuth2', 19),
    (67, 'Java', 20), (68, 'Spring Boot', 20), (69, 'Git', 20), (70, 'Agile', 20),

    -- DevOps (Resumes 21-30)
    (71, 'Linux', 21), (72, 'AWS', 21), (73, 'Terraform', 21), (74, 'Ansible', 21),
    (75, 'Kubernetes', 22), (76, 'Prometheus', 22), (77, 'Grafana', 22), (78, 'Ansible', 22),
    (79, 'AWS EKS', 23), (80, 'Docker', 23), (81, 'Helm', 23), (82, 'Python', 23),
    (83, 'Jenkins', 24), (84, 'GitLab CI', 24), (85, 'Bash', 24), (86, 'VMware', 24),
    (87, 'Ceph', 25), (88, 'Proxmox', 25), (89, 'Linux', 25),
    (90, 'Azure', 26), (91, 'ArgoCD', 26), (92, 'Kubernetes', 26), (93, 'GitOps', 26),
    (94, 'Linux', 27), (95, 'AWS', 27), (96, 'DevSecOps', 27), (97, 'Security Scanning', 27),
    (98, 'Docker', 28), (99, 'GitHub Actions', 28), (100, 'Kubernetes', 28),
    (101, 'Terraform', 29), (102, 'Prometheus', 29), (103, 'Linux', 29), (104, 'DNS/DHCP', 29),
    (105, 'Azure', 30), (106, 'Windows Server', 30), (107, 'Active Directory', 30), (108, 'Hyper-V', 30);

SELECT setval('resume_skills_seq', (SELECT MAX(id) FROM resume_skills));

-- ==========================================
-- 10. RESUME LANGUAGES
-- ==========================================
INSERT INTO resume_languages (id, resume_id, language)
VALUES
    (1, 1, 'English'),
    (2, 2, 'English'),
    (3, 3, 'English'), (4, 3, 'French'),
    (5, 6, 'English'), (6, 6, 'Irish (Basic)'),
    (7, 11, 'Dutch'), (8, 11, 'English'),
    (9, 12, 'English'), (10, 12, 'Hindi'),
    (11, 13, 'Dutch'), (12, 13, 'English'),
    (13, 14, 'Bulgarian'), (14, 14, 'English'),
    (15, 15, 'Dutch'), (16, 15, 'English'),
    (17, 16, 'Mandarin'), (18, 16, 'English'),
    (19, 17, 'Dutch'), (20, 17, 'English'),
    (21, 18, 'Italian'), (22, 18, 'English'), (23, 18, 'Dutch (Basic)'),
    (24, 19, 'Dutch'), (25, 19, 'English'),
    (26, 20, 'Arabic'), (27, 20, 'English'),
    (28, 21, 'English'),
    (29, 22, 'Polish'), (30, 22, 'English'),
    (31, 23, 'English'),
    (32, 24, 'English'),
    (33, 26, 'English'),
    (34, 28, 'Spanish'), (35, 28, 'English'),
    (36, 30, 'English');

-- Using the implicit sequence created by PostgreSQL for the SERIAL column
SELECT setval('resume_languages_id_seq', (SELECT MAX(id) FROM resume_languages));
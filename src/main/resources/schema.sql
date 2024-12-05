---------------------------------------
--  Dropping existing Database Tables  --
---------------------------------------
DROP TABLE IF EXISTS certification CASCADE;
DROP TYPE IF EXISTS degree_type_enum CASCADE;
DROP TYPE IF EXISTS gender_enum CASCADE;
DROP TABLE IF EXISTS volunteer CASCADE;
DROP TABLE IF EXISTS project CASCADE;
DROP TABLE IF EXISTS resume_languages CASCADE;
DROP TABLE IF EXISTS resume_skills CASCADE;
DROP TABLE IF EXISTS education CASCADE;
DROP TABLE IF EXISTS work_experience CASCADE;
DROP TABLE IF EXISTS gender CASCADE;
DROP TABLE IF EXISTS resume CASCADE;
DROP TABLE IF EXISTS scored_resume CASCADE;
DROP TABLE IF EXISTS document CASCADE;
DROP TABLE IF EXISTS vacancy CASCADE;
DROP TABLE IF EXISTS resume_vacancy CASCADE;
DROP TYPE IF EXISTS employment_type_enum CASCADE;
DROP TYPE IF EXISTS work_mode_enum CASCADE;
DROP TYPE IF EXISTS experience_level_enum CASCADE;
DROP TYPE IF EXISTS vacancy_status_enum CASCADE;



-- Drop sequences if they exist
DROP SEQUENCE IF EXISTS certification_seq;
DROP SEQUENCE IF EXISTS resume_skills_seq;
DROP SEQUENCE IF EXISTS education_seq;
DROP SEQUENCE IF EXISTS project_seq;
DROP SEQUENCE IF EXISTS resume_seq;
DROP SEQUENCE IF EXISTS scored_resume_seq;
DROP SEQUENCE IF EXISTS volunteer_seq;
DROP SEQUENCE IF EXISTS work_experience_seq;
DROP SEQUENCE IF EXISTS gender_seq;
DROP SEQUENCE IF EXISTS document_seq;
DROP SEQUENCE IF EXISTS vacancy_seq;



---------------------------------------
--  Creation of Tables  --
---------------------------------------
CREATE TYPE employment_type_enum AS ENUM (
    'FULL_TIME',
    'PART_TIME',
    'CONTRACT',
    'INTERNSHIP',
    'TEMPORARY'
    );

-- Create work mode enum
CREATE TYPE work_mode_enum AS ENUM (
    'REMOTE',
    'HYBRID',
    'ON_SITE'
    );

-- Create experience level enum
CREATE TYPE experience_level_enum AS ENUM (
    'ENTRY',
    'JUNIOR',
    'MID',
    'SENIOR',
    'LEAD',
    'PRINCIPAL'
    );

-- Create vacancy status enum
CREATE TYPE vacancy_status_enum AS ENUM (
    'OPEN',
    'CLOSED',
    'ON_HOLD',
    'FILLED'
    );

CREATE TABLE vacancy (
                         id SERIAL PRIMARY KEY,
                         vacancy_name VARCHAR(255) NOT NULL,
                         employment_type employment_type_enum,
                         work_mode work_mode_enum,
                         experience_level experience_level_enum,
                         status vacancy_status_enum NOT NULL DEFAULT 'OPEN',
                         location VARCHAR(255),
                         salary_range VARCHAR(100),
                         description TEXT,
                         post_date DATE
);

CREATE SEQUENCE vacancy_seq;

COMMENT ON TABLE vacancy IS 'Table to store job vacancy details';
COMMENT ON COLUMN vacancy.vacancy_name IS 'Title or name of the job vacancy';
COMMENT ON COLUMN vacancy.employment_type IS 'Type of employment (full-time, part-time, etc.)';
COMMENT ON COLUMN vacancy.work_mode IS 'Work mode of the position (remote, hybrid, on-site)';
COMMENT ON COLUMN vacancy.experience_level IS 'Required experience level for the position';
COMMENT ON COLUMN vacancy.status IS 'Current status of the vacancy';
COMMENT ON COLUMN vacancy.location IS 'Physical location or region for the position';
COMMENT ON COLUMN vacancy.salary_range IS 'Expected salary range for the position';
COMMENT ON COLUMN vacancy.description IS 'Detailed description of the vacancy including requirements';
COMMENT ON COLUMN vacancy.post_date IS 'Date when the vacancy was posted';


-- Resume Table
CREATE TABLE resume
(
    id                   SERIAL PRIMARY KEY,
    vacancy_id           INTEGER,
    first_name           VARCHAR(255) NOT NULL,
    last_name            VARCHAR(255) NOT NULL,
    email                VARCHAR(100) NOT NULL,
    gender               VARCHAR(255) ,
    date_of_birth        DATE         ,
    portfolio_url        VARCHAR(255) ,
    drivers_license      BOOLEAN      ,
    citizenship          VARCHAR(100) ,
    telephone            VARCHAR(14)  ,
    address              VARCHAR(255) ,
    work_permit_required BOOLEAN DEFAULT FALSE,
    visa_required        BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_vacancy
        FOREIGN KEY (vacancy_id)
            REFERENCES vacancy(id)
            ON DELETE CASCADE
);

CREATE SEQUENCE resume_seq;

-- Add comments for resume table
COMMENT ON TABLE resume IS 'Table to store personal and contact details of the person.';
COMMENT ON COLUMN resume.vacancy_id IS 'Foreign key reference to the vacancy the resume is submitted for';
COMMENT ON COLUMN resume.first_name IS 'First name of the person';
COMMENT ON COLUMN resume.last_name IS 'Last name of the person';
COMMENT ON COLUMN resume.email IS 'Email address of the person';
COMMENT ON COLUMN resume.gender IS 'Gender of the person';
COMMENT ON COLUMN resume.date_of_birth IS 'Date of birth of the person';
COMMENT ON COLUMN resume.portfolio_url IS 'URL of the persons portfolio';
COMMENT ON COLUMN resume.drivers_license IS 'Indicates if the person has a driver s license';
COMMENT ON COLUMN resume.citizenship IS 'Citizenship of the person';
COMMENT ON COLUMN resume.telephone IS 'Phone number of the person';
COMMENT ON COLUMN resume.address IS 'Address of the person';
COMMENT ON COLUMN resume.work_permit_required IS 'Indicates if the person requires a work permit';
COMMENT ON COLUMN resume.visa_required IS 'Indicates if the person requires a visa';


-- Create gender table
CREATE TABLE gender
(
    id          SERIAL PRIMARY KEY,
    gender_type VARCHAR(255)
);

-- Create sequence for gender table (if needed)
CREATE SEQUENCE gender_seq;

-- Insert gender types
INSERT INTO gender (gender_type)
VALUES ('MALE'),
       ('FEMALE');

-- Add comments for gender table
COMMENT ON TABLE gender IS 'Table to store gender types.';
COMMENT ON COLUMN gender.gender_type IS 'The gender of the person (e.g., MALE, FEMALE).';

-- Create type for gender enum
CREATE TYPE gender_enum AS ENUM (
    'MALE',
    'FEMALE'
    );

-- Resume Work Experience Table
CREATE TABLE work_experience
(
    id           SERIAL PRIMARY KEY,
    company_name VARCHAR(255) ,
    job_title    VARCHAR(255) ,
    description  TEXT         ,
    start_date   DATE         ,
    end_date     DATE         ,
    resume_id    INTEGER      NOT NULL,
    CONSTRAINT fk_resume
        FOREIGN KEY (resume_id)
            REFERENCES resume (id)
            ON DELETE CASCADE
);

CREATE SEQUENCE work_experience_seq;

COMMENT ON TABLE work_experience IS 'Table to store details of work experiences of a candidate';
COMMENT ON COLUMN work_experience.company_name IS 'Name of the company the candidate worked for';
COMMENT ON COLUMN work_experience.job_title IS 'Job title of the candidate at the company';
COMMENT ON COLUMN work_experience.description IS 'Description of the responsibilities and achievements during the job';
COMMENT ON COLUMN work_experience.start_date IS 'The date when the candidate started the job';
COMMENT ON COLUMN work_experience.end_date IS 'The date when the candidate ended the job';
COMMENT ON COLUMN work_experience.resume_id IS 'The resume_id linking to the candidates resume';


-- Resume Education Table
CREATE TABLE education
(
    id               SERIAL PRIMARY KEY,
    institution_name VARCHAR(255) ,
    degree_type      VARCHAR(25)  ,
    major_subject    VARCHAR(255) ,
    enrollment_date  DATE         ,
    graduation_date  DATE         ,
    resume_id        INTEGER      NOT NULL,
    CONSTRAINT fk_resume_education
        FOREIGN KEY (resume_id)
            REFERENCES resume (id)
            ON DELETE CASCADE
);

CREATE SEQUENCE education_seq;

-- Add comments for education table
COMMENT ON TABLE education IS 'Table to store educational qualifications for a resume.';
COMMENT ON COLUMN education.institution_name IS 'Name of the educational institution.';
COMMENT ON COLUMN education.degree_type IS 'Type of degree earned (e.g., Associate, Bachelor, Master, Doctorate, Certificate, Bootcamp, Other).';
COMMENT ON COLUMN education.major_subject IS 'Area of study.';
COMMENT ON COLUMN education.enrollment_date IS 'Start date of education.';
COMMENT ON COLUMN education.graduation_date IS 'End date of education.';
COMMENT ON COLUMN education.resume_id IS 'ID of the associated resume.';

-- Create type for degree_type
CREATE TYPE degree_type_enum AS ENUM (
    'Associate',
    'Bachelor',
    'Master',
    'Doctorate',
    'Certificate',
    'Bootcamp',
    'Other'
    );


-- Resume Skills Table
CREATE TABLE resume_skills
(
    id        SERIAL PRIMARY KEY,
    skill     VARCHAR(255) ,
    resume_id INTEGER      NOT NULL,
    CONSTRAINT fk_resume_skills
        FOREIGN KEY (resume_id)
            REFERENCES resume (id)
            ON DELETE CASCADE
);

-- Create sequence for resume_skills
CREATE SEQUENCE resume_skills_seq;

-- Add comments for the resume_skills table and columns
COMMENT ON TABLE resume_skills IS 'Table to store skills associated with a resume.';
COMMENT ON COLUMN resume_skills.skill IS 'The skill of the person, e.g. Java, Spring Boot, SQL.';
COMMENT ON COLUMN resume_skills.resume_id IS 'Foreign key linking the skill to the associated resume.';

-- Create languages table
CREATE TABLE resume_languages
(
    id        SERIAL PRIMARY KEY,
    resume_id INTEGER      ,
    language  VARCHAR(255) NOT NULL,
    CONSTRAINT fk_resume_languages
        FOREIGN KEY (resume_id)
            REFERENCES resume (id)
            ON DELETE CASCADE
);

-- Add comments for the resume_languages table and columns
COMMENT ON TABLE resume_languages IS 'Table to store languages known by the person.';

COMMENT ON COLUMN resume_languages.resume_id IS 'Foreign key linking the language to the associated resume.';
COMMENT ON COLUMN resume_languages.language IS 'Name of the language known by the person.';

-- Resume Projects Table
CREATE TABLE project
(
    id           SERIAL PRIMARY KEY,
    project_name VARCHAR(255) ,
    description  TEXT         ,
    resume_id    INTEGER      NOT NULL,
    CONSTRAINT fk_project_resume
        FOREIGN KEY (resume_id)
            REFERENCES resume (id)
            ON DELETE CASCADE
);

CREATE SEQUENCE project_seq;

COMMENT ON TABLE project IS 'Table to store projects associated with a candidates resume';
COMMENT ON COLUMN project.project_name IS 'Name of the project the candidate worked on';
COMMENT ON COLUMN project.description IS 'Description of the project detailing the candidates contributions';
COMMENT ON COLUMN project.resume_id IS 'The resume_id linking to the candidates resume';

-- Resume Volunteer Work Table
CREATE TABLE volunteer
(
    id                SERIAL PRIMARY KEY,
    organization_name VARCHAR(255) ,
    role              VARCHAR(255) ,
    description       TEXT         ,
    start_date        DATE         ,
    end_date          DATE         ,
    resume_id         INTEGER      NOT NULL,
    CONSTRAINT fk_resume
        FOREIGN KEY (resume_id)
            REFERENCES resume (id)
            ON DELETE CASCADE
);

CREATE SEQUENCE volunteer_seq;

COMMENT ON TABLE volunteer IS 'Table to store details of volunteer work experiences of a candidate';
COMMENT ON COLUMN volunteer.organization_name IS 'Name of the organization where the volunteer work was done';
COMMENT ON COLUMN volunteer.role IS 'Role the candidate held during their volunteer work';
COMMENT ON COLUMN volunteer.description IS 'Description of the volunteer work performed';
COMMENT ON COLUMN volunteer.start_date IS 'The date when the volunteer work started';
COMMENT ON COLUMN volunteer.end_date IS 'The date when the volunteer work ended';
COMMENT ON COLUMN volunteer.resume_id IS 'The resume_id linking to the candidates resume';


-- Create the certification table
CREATE TABLE certification
(
    id                 SERIAL PRIMARY KEY,
    certification_name VARCHAR(255) ,
    description        TEXT         ,
    date               DATE         ,
    resume_id          INTEGER      NOT NULL,
    CONSTRAINT fk_resume
        FOREIGN KEY (resume_id)
            REFERENCES resume (id)
            ON DELETE CASCADE
);

CREATE SEQUENCE certification_seq;

COMMENT ON TABLE certification IS 'Table to store details of certifications obtained by candidates';
COMMENT ON COLUMN certification.certification_name IS 'Name of the Certification';
COMMENT ON COLUMN certification.description IS 'Description of the Certification';
COMMENT ON COLUMN certification.date IS 'Acquisition date of the Certification';
COMMENT ON COLUMN certification.resume_id IS 'The resume_id linking to the candidates resume';


-- Scored Resume Table
CREATE TABLE scored_resume
(
    id                    SERIAL PRIMARY KEY,
    work_experience_score DOUBLE PRECISION,
    education_score       DOUBLE PRECISION,
    skills_score          DOUBLE PRECISION,
    languages_score       DOUBLE PRECISION,
    projects_score        DOUBLE PRECISION,
    volunteer_work_score  DOUBLE PRECISION,
    certifications_score  DOUBLE PRECISION,
    total_score           DOUBLE PRECISION
);

CREATE SEQUENCE scored_resume_seq;

-- Add comments for resume table
COMMENT ON TABLE scored_resume IS 'Table to store the score of each resume';
COMMENT ON COLUMN scored_resume.work_experience_score IS 'The score for the work experience';
COMMENT ON COLUMN scored_resume.education_score IS 'The score for the education';
COMMENT ON COLUMN scored_resume.skills_score IS 'The score for the skills';
COMMENT ON COLUMN scored_resume.languages_score IS 'The score for the languages';
COMMENT ON COLUMN scored_resume.projects_score IS 'The score for the  projects';
COMMENT ON COLUMN scored_resume.volunteer_work_score IS 'The score for the volunteer work';
COMMENT ON COLUMN scored_resume.certifications_score IS 'The score for the certifications';
COMMENT ON COLUMN scored_resume.total_score IS 'The total score of each resume';

-- Document Table
CREATE TABLE document
(
    id                  SERIAL PRIMARY KEY,
    filename            VARCHAR(50),
    content             TEXT,
    upload_date         TIMESTAMP
);

CREATE SEQUENCE document_seq;

-- Add comments for resume table
COMMENT ON TABLE document IS 'Table to store the raw text of each resume uploaded';
COMMENT ON COLUMN document.filename IS 'The name of the file uploaded';
COMMENT ON COLUMN document.content IS 'The contents of the document';
COMMENT ON COLUMN document.upload_date IS 'The date the document got uploaded';




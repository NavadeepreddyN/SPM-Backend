-- Create Database
CREATE DATABASE IF NOT EXISTS group_project_db;
USE group_project_db;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    university_id VARCHAR(15) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    role ENUM('STUDENT', 'TEACHER') NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    section_id VARCHAR(20) NOT NULL
);

-- Projects Table
CREATE TABLE IF NOT EXISTS projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    deadline DATE NOT NULL,
    teacher_id BIGINT,
    section_id VARCHAR(20) NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Submissions Table
CREATE TABLE IF NOT EXISTS submissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT,
    student_id BIGINT,
    submission_link VARCHAR(500) NOT NULL,
    submission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Grades Table
CREATE TABLE IF NOT EXISTS grades (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    submission_id BIGINT,
    score DECIMAL(5, 2),
    feedback_text TEXT,
    graded_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (submission_id) REFERENCES submissions(id) ON DELETE CASCADE
);

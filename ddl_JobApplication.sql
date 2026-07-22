CREATE TABLE job_applications
(
    id           BIGINT AUTO_INCREMENT         NOT NULL,
    created_at   datetime                      NOT NULL,
    created_by   VARCHAR(20)                   NOT NULL,
    updated_at   datetime                      NOT NULL,
    updated_by   VARCHAR(20)                   NULL,
    user_id      BIGINT                        NOT NULL,
    job_id       BIGINT                        NOT NULL,
    applied_at   datetime    DEFAULT NOW()     NOT NULL,
    status       VARCHAR(50) DEFAULT 'PENDING' NOT NULL,
    cover_letter LONGTEXT                      NULL,
    notes        LONGTEXT                      NULL,
    CONSTRAINT pk_job_applications PRIMARY KEY (id)
);

ALTER TABLE job_applications
    ADD CONSTRAINT FK_JOB_APPLICATIONS_ON_JOB FOREIGN KEY (job_id) REFERENCES jobs (id) ON DELETE CASCADE;

ALTER TABLE job_applications
    ADD CONSTRAINT FK_JOB_APPLICATIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;
CREATE TABLE job_applications
(
    id           BIGINT AUTO_INCREMENT         NOT NULL,
    created_at   datetime                      NOT NULL,
    created_by   VARCHAR(20)                   NOT NULL,
    updated_at   datetime                      NOT NULL,
    updated_by   VARCHAR(20)                   NULL,
    user_id      BIGINT                        NOT NULL,
    job_id       BIGINT                        NOT NULL,
    applied_at   datetime    DEFAULT NOW()     NOT NULL,
    status       VARCHAR(50) DEFAULT 'PENDING' NOT NULL,
    cover_letter LONGTEXT                      NULL,
    notes        LONGTEXT                      NULL,
    CONSTRAINT pk_job_applications PRIMARY KEY (id)
);

ALTER TABLE job_applications
    ADD CONSTRAINT FK_JOB_APPLICATIONS_ON_JOB FOREIGN KEY (job_id) REFERENCES jobs (id) ON DELETE CASCADE;

ALTER TABLE job_applications
    ADD CONSTRAINT FK_JOB_APPLICATIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;
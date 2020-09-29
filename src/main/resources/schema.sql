CREATE TABLE IF NOT EXISTS users
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    user_name    VARCHAR(30)           NOT NULL,
    password     VARCHAR(30)           NOT NULL,
    email        VARCHAR(255)          NOT NULL,
    phone_number VARCHAR(20)           NOT NULL,
    role         VARCHAR(20)           NOT NULL,
    CONSTRAINT PK_users PRIMARY KEY (id),
    CONSTRAINT UQ_user_name UNIQUE (user_name),
    CONSTRAINT UQ_user_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS timeslots
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    start_time TIME                  NOT NULL,
    CONSTRAINT PK_timeslots PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS feedbacks
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    rate          BIGINT                NOT NULL,
    text          VARCHAR(255)          NOT NULL,
    client_id     BIGINT                NOT NULL,
    specialist_id BIGINT                NOT NULL,
    date_time     DATETIME              NOT NULL,
    CONSTRAINT PK_feedbacks PRIMARY KEY (id),
    CONSTRAINT FK_appointments_feedbacks_client FOREIGN KEY (client_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT FK_appointments_feedbacks_specialist FOREIGN KEY (specialist_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS services
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(30)           NOT NULL,
    description VARCHAR(255)          NOT NULL,
    price       DECIMAL               NOT NULL,
    CONSTRAINT PK_services PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS specialists_services
(
    specialist_id BIGINT NOT NULL,
    service_id    BIGINT NOT NULL,
    CONSTRAINT PK_specialists_services PRIMARY KEY (specialist_id, service_id),
    CONSTRAINT FK_specialists_services_specialist FOREIGN KEY (specialist_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT FK_specialists_services_service FOREIGN KEY (service_id) REFERENCES services (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS appointments
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    timeslot_id   BIGINT                NOT NULL,
    date          DATE                  NOT NULL,
    client_id     BIGINT                NOT NULL,
    specialist_id BIGINT                NOT NULL,
    service_id    BIGINT                NOT NULL,
    is_paid       BOOLEAN               NOT NULL,
    is_done       BOOLEAN               NOT NULL,
    CONSTRAINT PK_appointments PRIMARY KEY (id),
    CONSTRAINT FK_appointments_timeslots FOREIGN KEY (timeslot_id) REFERENCES timeslots (id) ON DELETE CASCADE,
    CONSTRAINT FK_appointments_users_client FOREIGN KEY (client_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT FK_appointments_users_specialist FOREIGN KEY (specialist_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT FK_appointments_services FOREIGN KEY (service_id) REFERENCES services (id) ON DELETE CASCADE
);

INSERT INTO users (user_name, password, email, phone_number, role)
values ('anna', 'password', 'annaK@gmail.com', '+380501234567', 'CLIENT');

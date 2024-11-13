CREATE DATABASE BloodBankManagement;
USE BloodBankManagement;

-- Donor table
CREATE TABLE Donor
(
    Donor_id           VARCHAR(50) PRIMARY KEY,
    Name               VARCHAR(100),
    Donor_nic          VARCHAR(20),
    Address            VARCHAR(255),
    E_mail             VARCHAR(100),
    Blood_group        ENUM('A_POSITIVE', 'A_NEGATIVE', 'B_POSITIVE', 'B_NEGATIVE', 'AB_POSITIVE', 'AB_NEGATIVE', 'O_POSITIVE', 'O_NEGATIVE'),
    Gender             ENUM('MALE', 'FEMALE', 'OTHER'),
    Dob                DATE,
    Last_donation_date DATE
);

-- Health_checkup table with cascades
CREATE TABLE Health_checkup
(
    Checkup_id      VARCHAR(50) PRIMARY KEY,
    Donor_id        VARCHAR(50),
    Health_status   VARCHAR(50),
    Date_of_checkup DATE,
    weight          DECIMAL(5,2),
    sugar_level     DECIMAL(5,2),
    blood_pressure  VARCHAR(20),
    FOREIGN KEY (Donor_id) REFERENCES Donor (Donor_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Blood_campaign table
CREATE TABLE Blood_campaign
(
    Blood_campaign_id VARCHAR(50) PRIMARY KEY,
    Name              VARCHAR(100),
    Address           VARCHAR(255),
    Start_date        DATE,
    End_date          DATE,
    Status            ENUM('ACTIVE', 'INACTIVE', 'PENDING', 'COMPLETED'),
    Collected_units   INT
);

-- Inventory table
CREATE TABLE Inventory
(
    Inventory_id VARCHAR(50) PRIMARY KEY,
    Item_name    VARCHAR(100),
    Attribute    VARCHAR(100),
    Status       ENUM('ACTIVE', 'INACTIVE', 'PENDING', 'COMPLETED'),
    Expiry_date  DATE,
    Qty          INT
);

-- Supplier table
CREATE TABLE Supplier
(
    Supplier_id VARCHAR(50) PRIMARY KEY,
    Name        VARCHAR(100),
    Address     VARCHAR(255),
    E_mail      VARCHAR(100),
    Description TEXT
);

-- Supplier_Inventory many-to-many relationship table with cascades
CREATE TABLE Supplier_Inventory
(
    Supplier_id               VARCHAR(50),
    Inventory_id              VARCHAR(50),
    Supplier_inventory_detail VARCHAR(255),
    PRIMARY KEY (Supplier_id, Inventory_id),
    FOREIGN KEY (Supplier_id) REFERENCES Supplier (Supplier_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (Inventory_id) REFERENCES Inventory (Inventory_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Blood_stock table
CREATE TABLE Blood_stock
(
    Blood_id    VARCHAR(50) PRIMARY KEY,
    Blood_group ENUM('A POSITIVE', 'A NEGATIVE', 'B POSITIVE', 'B NEGATIVE', 'AB POSITIVE', 'AB NEGATIVE', 'O POSITIVE', 'O NEGATIVE'),
    Qty         INT,
    Expiry_date DATE,
    Is_verified BOOLEAN
);

-- Blood_test table with cascades
CREATE TABLE Blood_test
(
    Test_id     VARCHAR(50) PRIMARY KEY,
    Blood_id    VARCHAR(50),
    Test_date   DATE,
    Test_result TEXT,
    FOREIGN KEY (Blood_id) REFERENCES Blood_stock (Blood_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Blood_donation table with cascades
CREATE TABLE Blood_donation
(
    Donation_id       VARCHAR(50) PRIMARY KEY,
    Blood_campaign_id VARCHAR(50),
    Health_checkup_id VARCHAR(50),
    Blood_group       ENUM('A_POSITIVE', 'A_NEGATIVE', 'B_POSITIVE', 'B_NEGATIVE', 'AB_POSITIVE', 'AB_NEGATIVE', 'O_POSITIVE', 'O_NEGATIVE'),
    Qty               INT,
    Date_of_donation  DATE,
    FOREIGN KEY (Blood_campaign_id) REFERENCES Blood_campaign (Blood_campaign_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (Health_checkup_id) REFERENCES Health_checkup (Checkup_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Employee table
CREATE TABLE Employee
(
    employee_id VARCHAR(50) PRIMARY KEY,
    Name        VARCHAR(100),
    nfc         VARCHAR(20),
    Address     VARCHAR(255),
    E_mail      VARCHAR(100),
    Role        VARCHAR(50),
    Status      ENUM('ACTIVE', 'INACTIVE', 'PENDING', 'COMPLETED')
);

-- Hospital table
CREATE TABLE Hospital
(
    Hospital_id    VARCHAR(50) PRIMARY KEY,
    Name           VARCHAR(100),
    Address        VARCHAR(255),
    Contact_number VARCHAR(15),
    Email          VARCHAR(100),
    Type           VARCHAR(50)
);

-- Reserved_blood table with cascades
CREATE TABLE Reserved_blood
(
    Reserved_id   VARCHAR(50) PRIMARY KEY,
    Blood_id      VARCHAR(50),
    Hospital_id   VARCHAR(50),
    Reserved_date DATE,
    Reserved_qty  INT,
    FOREIGN KEY (Blood_id) REFERENCES Blood_stock (Blood_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (Hospital_id) REFERENCES Hospital (Hospital_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Blood_request table with cascades
CREATE TABLE Blood_request
(
    Request_id       VARCHAR(50) PRIMARY KEY,
    Hospital_id      VARCHAR(50),
    Blood_group      ENUM('A POSITIVE', 'A NEGATIVE', 'B POSITIVE', 'B NEGATIVE', 'AB POSITIVE', 'AB NEGATIVE', 'O POSITIVE', 'O NEGATIVE'),
    Compatibility_id VARCHAR(50),
    Date_of_request  DATE,
    Qty              INT,
    Status           ENUM('ACTIVE', 'INACTIVE', 'PENDING', 'COMPLETED'),
    FOREIGN KEY (Hospital_id) REFERENCES Hospital (Hospital_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Blood_request_detail table with cascades
CREATE TABLE Blood_request_detail
(
    Blood_request_id VARCHAR(50),
    Blood_id         VARCHAR(50),
    PRIMARY KEY (Blood_request_id, Blood_id),
    FOREIGN KEY (Blood_request_id) REFERENCES Blood_request (Request_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (Blood_id) REFERENCES Blood_stock (Blood_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

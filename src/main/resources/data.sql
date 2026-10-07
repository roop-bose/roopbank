-- =====================================================
-- ROLES
-- =====================================================

INSERT INTO roles
    (role_name, created_by, created_at)
VALUES
    ('ROLE_CUSTOMER', 'SYSTEM', CURRENT_TIMESTAMP)
ON DUPLICATE KEY UPDATE
    role_name = role_name;


INSERT INTO roles
    (role_name, created_by, created_at)
VALUES
    ('ROLE_ADMIN', 'SYSTEM', CURRENT_TIMESTAMP)
ON DUPLICATE KEY UPDATE
    role_name = role_name;


-- =====================================================
-- LOAN PRODUCTS
-- =====================================================

INSERT INTO loan_products
    (loan_type, interest_rate, max_tenure_months, max_loan_amount, active)
VALUES
    ('HOME_LOAN', 8.50, 360, 10000000.00, true)
ON DUPLICATE KEY UPDATE
    interest_rate = 8.50,
    max_tenure_months = 360,
    max_loan_amount = 10000000.00,
    active = true;


INSERT INTO loan_products
    (loan_type, interest_rate, max_tenure_months, max_loan_amount, active)
VALUES
    ('CAR_LOAN', 9.50, 84, 3000000.00, true)
ON DUPLICATE KEY UPDATE
    interest_rate = 9.50,
    max_tenure_months = 84,
    max_loan_amount = 3000000.00,
    active = true;


INSERT INTO loan_products
    (loan_type, interest_rate, max_tenure_months, max_loan_amount, active)
VALUES
    ('PERSONAL_LOAN', 12.50, 60, 1000000.00, true)
ON DUPLICATE KEY UPDATE
    interest_rate = 12.50,
    max_tenure_months = 60,
    max_loan_amount = 1000000.00,
    active = true;


INSERT INTO loan_products
    (loan_type, interest_rate, max_tenure_months, max_loan_amount, active)
VALUES
    ('EDUCATION_LOAN', 7.50, 120, 5000000.00, true)
ON DUPLICATE KEY UPDATE
    interest_rate = 7.50,
    max_tenure_months = 120,
    max_loan_amount = 5000000.00,
    active = true;


INSERT INTO loan_products
    (loan_type, interest_rate, max_tenure_months, max_loan_amount, active)
VALUES
    ('BUSINESS_LOAN', 11.00, 120, 5000000.00, true)
ON DUPLICATE KEY UPDATE
    interest_rate = 11.00,
    max_tenure_months = 120,
    max_loan_amount = 5000000.00,
    active = true;
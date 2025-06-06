-- 员工信息表
CREATE TABLE emp (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL,
    gender TINYINT DEFAULT 1 NOT NULL, -- 1:男 0:女
    age INT NOT NULL,
    password VARCHAR(100) NOT NULL,
    role TINYINT DEFAULT 0 NOT NULL -- 0:员工 1:管理员
);

-- 考勤记录表
CREATE TABLE check_record (
    id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT NOT NULL,
    check_in_time DATETIME,
    check_out_time DATETIME
);

-- 请假记录表
CREATE TABLE leave_record (
    id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT NOT NULL,
    date DATE NOT NULL,
    reason VARCHAR(255),
    status TINYINT DEFAULT 0 NOT NULL -- 0：未批准 1：已批准
);

-- 日历表，用于存储工作日和假期
CREATE TABLE calendar (
    date_value DATE PRIMARY KEY,
    is_workday BOOLEAN NOT NULL -- 1为上班日, 0为放假日
);





-- -- 状态记录表
-- CREATE TABLE status_record (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     emp_id INT NOT NULL,
--     date DATE NOT NULL,
--     status TINYINT DEFAULT 2 NOT NULL -- 0:正常 1:请假 2:缺勤
-- );
-- DROP TABLE status_record;

DROP TABLE calendar;
DROP TABLE emp;
DROP TABLE check_record;
DROP TABLE leave_record;


-- 管理员账号
INSERT INTO emp VALUES ( 1, "admin", 1, 18, "kaishen", 1 );

INSERT INTO emp (id, name, gender, age, password, role)
VALUES (2, '张三', 1, 28, 'zhangsan', 0),
    (3, '李红', 0, 32, 'lihong', 0);

-- 1. 获取2025年5月所有工作日
WITH work_days AS (
    SELECT date_value
    FROM calendar
    WHERE 
        date_value BETWEEN '2025-05-01' AND '2025-05-31'
        AND is_workday = 1
),-- 2. 每位员工与工作日的组合
emp_days AS (
    SELECT 
        e.id AS emp_id,
        e.name,
        w.date_value
    FROM emp e
    CROSS JOIN work_days w
    WHERE e.role = 0 -- 只统计员工角色（非管理员）
),-- 3. 关联请假记录（只考虑已批准的请假）
leave_data AS (
    SELECT
        ed.emp_id,
        ed.date_value,
        MAX(l.status) AS leave_approved
    FROM emp_days ed
    LEFT JOIN leave_record l ON 
        ed.emp_id = l.emp_id 
        AND ed.date_value = l.date
        AND l.status = 1
    GROUP BY ed.emp_id, ed.date_value
),-- 4. 关联打卡记录（假设每天最多一条记录）
check_data AS (
    SELECT
        ed.emp_id,
        ed.date_value,
        -- 直接获取打卡时间（无需聚合）
        MIN(cr.check_in_time) AS check_in,  -- 使用MIN确保单值
        MAX(cr.check_out_time) AS check_out -- 使用MAX确保单值
    FROM emp_days ed
    LEFT JOIN check_record cr ON 
        ed.emp_id = cr.emp_id
        AND DATE(cr.check_in_time) = ed.date_value
    GROUP BY ed.emp_id, ed.date_value
),-- 5. 计算每日考勤状态
daily_status AS (
    SELECT 
        ed.emp_id,
        ed.name,
        ed.date_value,
        CASE 
            WHEN ld.leave_approved = 1 THEN '请假'
            WHEN cd.check_in IS NULL AND cd.check_out IS NULL THEN '缺勤'
            WHEN cd.check_in IS NOT NULL AND cd.check_out IS NULL THEN '缺卡（下班）'
            WHEN cd.check_in IS NULL AND cd.check_out IS NOT NULL THEN '缺卡（上班）'
            WHEN TIME(cd.check_in) > '09:00:00' AND TIME(cd.check_out) < '18:00:00' THEN '迟到+早退'
            WHEN TIME(cd.check_in) > '09:00:00' THEN '迟到'
            WHEN TIME(cd.check_out) < '18:00:00' THEN '早退'
            ELSE '正常'
        END AS status
    FROM emp_days ed
    LEFT JOIN leave_data ld ON 
        ed.emp_id = ld.emp_id AND ed.date_value = ld.date_value
    LEFT JOIN check_data cd ON 
        ed.emp_id = cd.emp_id AND ed.date_value = cd.date_value
)-- 6. 按月汇总考勤数据
SELECT 
    emp_id,
    name,
    COUNT(*) AS work_days_total,
    SUM(CASE status 
        WHEN '正常' THEN 1 ELSE 0 END) AS normal_days,
    SUM(CASE status 
        WHEN '迟到' THEN 1 ELSE 0 END) AS late_days,
    SUM(CASE status 
        WHEN '早退' THEN 1 ELSE 0 END) AS early_days,
    SUM(CASE status 
        WHEN '迟到+早退' THEN 1 ELSE 0 END) AS late_early_days,
    SUM(CASE WHEN status LIKE '缺卡%' THEN 1 ELSE 0 END) AS incomplete_days,
    SUM(CASE status 
        WHEN '缺勤' THEN 1 ELSE 0 END) AS absent_days,
    SUM(CASE status 
        WHEN '请假' THEN 1 ELSE 0 END) AS leave_days
FROM daily_status
GROUP BY emp_id, name
ORDER BY emp_id; -- 只统计员工角色（非管理员）

-- 删除现有数据（测试时使用）
DELETE FROM calendar WHERE date_value BETWEEN '2025-05-01' AND '2025-05-31';

-- 插入2025年5月日历数据
INSERT INTO calendar (date_value, is_workday)
VALUES
('2025-05-01', 0), -- 劳动节放假
('2025-05-02', 1), 
('2025-05-03', 0), -- 周六
('2025-05-04', 0), -- 周日
('2025-05-05', 1),
('2025-05-06', 1),
('2025-05-07', 1),
('2025-05-08', 1),
('2025-05-09', 1),
('2025-05-10', 0), -- 周六
('2025-05-11', 0), -- 周日
('2025-05-12', 1),
('2025-05-13', 1),
('2025-05-14', 1),
('2025-05-15', 1),
('2025-05-16', 1),
('2025-05-17', 0), -- 周六
('2025-05-18', 0), -- 周日
('2025-05-19', 1),
('2025-05-20', 1),
('2025-05-21', 1),
('2025-05-22', 1),
('2025-05-23', 1),
('2025-05-24', 0), -- 周六
('2025-05-25', 0), -- 周日
('2025-05-26', 1),
('2025-05-27', 1),
('2025-05-28', 1),
('2025-05-29', 1),
('2025-05-30', 1),
('2025-05-31', 0); -- 周六

-- 删除现有数据（测试时使用）
DELETE FROM leave_record WHERE date BETWEEN '2025-05-01' AND '2025-05-31';

-- 插入请假记录（假设有3位员工请假）
INSERT INTO leave_record (emp_id, date, reason, status)
VALUES
-- 员工1（ID=2）请病假（已批准）
(3, '2025-05-05', '感冒发烧', 1),
-- 员工1（ID=2）请事假（未批准）
(2, '2025-05-15', '家庭事务', 0),
-- 员工2（ID=3）请年假（已批准）
(3, '2025-05-12', '年假休息', 1),
(3, '2025-05-13', '年假休息', 1),
-- 员工3（ID=4）请病假（已批准）
(4, '2025-05-20', '牙科手术', 1),
(4, '2025-05-21', '术后恢复', 1),
-- 员工4（ID=5）请事假（已批准）
(5, '2025-05-28', '参加婚礼', 1);


-- 插入张三（id=1）的打卡记录
INSERT INTO check_record (emp_id, check_in_time, check_out_time) VALUES
-- 正常打卡
(1, '2025-05-02 08:45:22', '2025-05-02 18:10:05'),
(1, '2025-05-05 08:30:11', '2025-05-05 18:05:33'),  -- 请假日，但仍打卡
(1, '2025-05-06 08:55:01', '2025-05-06 18:20:15'),
(1, '2025-05-07 08:42:30', '2025-05-07 18:15:48'),
(1, '2025-05-08 08:58:19', '2025-05-08 18:08:27'),
(1, '2025-05-09 08:47:52', '2025-05-09 18:12:41'),
(1, '2025-05-12 08:51:34', '2025-05-12 18:25:03'),
(1, '2025-05-14 08:39:45', '2025-05-14 18:17:29'),
(1, '2025-05-15 08:53:27', '2025-05-15 18:22:56'),  -- 未批准请假日
(1, '2025-05-16 08:44:18', '2025-05-16 18:11:39'),
(1, '2025-05-19 08:56:03', '2025-05-19 18:19:47'),
(1, '2025-05-22 08:48:55', '2025-05-22 18:14:22'),
(1, '2025-05-23 08:52:41', '2025-05-23 18:08:33'),
(1, '2025-05-26 08:46:29', '2025-05-26 18:16:54'),
(1, '2025-05-27 08:57:12', '2025-05-27 18:23:18'),
(1, '2025-05-28 08:41:37', '2025-05-28 18:09:45'),
(1, '2025-05-29 08:54:08', '2025-05-29 18:18:26'),
(1, '2025-05-30 08:49:51', '2025-05-30 18:13:07'),-- 异常打卡
(1, '2025-05-13 09:15:22', '2025-05-13 18:05:33'),  -- 迟到
(1, '2025-05-20 08:44:11', '2025-05-20 17:45:19'),  -- 早退
(1, '2025-05-21 09:20:05', '2025-05-21 17:50:41'),  -- 迟到+早退
(1, '2025-05-31 08:42:33', NULL),                 -- 缺下班卡（周六但上班）
(1, NULL, '2025-05-31 18:22:15');                -- 缺上班卡（周六但上班）

-- 插入李四（id=2）的打卡记录
INSERT INTO check_record (emp_id, check_in_time, check_out_time) VALUES
-- 正常打卡
(2, '2025-05-02 08:50:11', '2025-05-02 18:12:44'),
(2, '2025-05-05 08:33:25', '2025-05-05 18:08:17'),
(2, '2025-05-06 08:47:39', '2025-05-06 18:15:28'),
(2, '2025-05-07 08:55:42', '2025-05-07 18:22:05'),
(2, '2025-05-08 08:41:18', '2025-05-08 18:09:36'),
(2, '2025-05-09 08:52:27', '2025-05-09 18:17:49'),
(2, '2025-05-14 08:46:53', '2025-05-14 18:14:22'),
(2, '2025-05-15 08:58:34', '2025-05-15 18:26:11'),
(2, '2025-05-16 08:44:07', '2025-05-16 18:11:58'),
(2, '2025-05-19 08:51:29', '2025-05-19 18:18:43'),
(2, '2025-05-22 08:48:16', '2025-05-22 18:13:27'),
(2, '2025-05-23 08:53:45', '2025-05-23 18:07:39'),
(2, '2025-05-26 08:42:31', '2025-05-26 18:15:54'),
(2, '2025-05-27 08:56:12', '2025-05-27 18:24:18'),
(2, '2025-05-28 08:39:47', '2025-05-28 18:08:45'),  -- 请假日，但仍打卡
(2, '2025-05-29 08:47:58', '2025-05-29 18:19:36'),
(2, '2025-05-30 08:50:21', '2025-05-30 18:12:47'),
-- 异常打卡
(2, '2025-05-12 09:05:33', '2025-05-12 18:10:22'),  -- 迟到（请假日但未打卡）
(2, '2025-05-13 08:44:18', '2025-05-13 17:55:41'),  -- 早退（请假日但未打卡）
(2, '2025-05-20 09:10:15', '2025-05-20 18:05:27'),  -- 迟到
(2, '2025-05-21 08:52:44', '2025-05-21 17:40:19');  -- 早退




INSERT INTO leave_record (emp_id, date, reason, status) VALUES
(2, '2025-06-03', 'Annual leave for family trip', 1),
(2, '2025-06-11', 'Medical appointment', 1),
(2, '2025-06-15', 'Urgent personal matter', 0),
(2, '2025-06-19', 'Recover from flu', 1),
(2, '2025-06-23', 'Attend wedding ceremony', 0),
(2, '2025-06-28', 'Mental health break', 1);
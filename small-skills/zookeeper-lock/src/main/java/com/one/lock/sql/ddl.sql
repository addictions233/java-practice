CREATE TABLE distributed_lock (
id INT PRIMARY KEY AUTO_INCREMENT, -- 锁的唯一标识
resource_key VARCHAR(255) NOT NULL, -- 锁的资源标识符
owner VARCHAR(255) NOT NULL, -- 锁的拥有者
created_time DATETIME NOT NULL, -- 锁的创建时间
expiration_time DATETIME NOT NULL -- 锁的过期时间
);


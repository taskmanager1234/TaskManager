//package com.company;
//
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import javax.sql.DataSource;
//
//public class InitPostgresSql {
//
//
//    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        jdbcTemplate.execute("create table if not exists task" +
//                "(" +
//                "id uuid NOT NULL " +
//                " primary key," +
//                "description varchar(50000)," +
//                "end_date timestamp not null," +
//                "reminder timestamp," +
//                "start_date timestamp not null," +
//                "CONSTRAINT fk_tj" +
//                " FOREIGN KEY (tasks_journal_id)" +
//                " REFERENCES task_journal(id)," +
//                "title varchar(110) not null" +
//                ");");
//
//        jdbcTemplate.execute("create table if not exists task_journal" +
//                "(" +
//                "id uuid not null " +
//                " primary key" +
//                ");");
//    }
//}

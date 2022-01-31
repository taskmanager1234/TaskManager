//package com.company;
//
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import javax.sql.DataSource;
//
//public class InitPostgresSql {
//
////TODO magic numbers
//    public static void init(){
//    DataSource dataSource =  DataSourceBuilder.create()
//            .driverClassName("org.postgresql.Driver")
//            .url("jdbc:postgresql://localhost:5432/postgres")
//            .username("postgres")
//            .password("postgres")
//            .build();
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

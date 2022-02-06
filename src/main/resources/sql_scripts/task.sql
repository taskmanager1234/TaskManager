create table if not exists task
(
    id varchar(40) not null
        constraint task_pkey
            primary key,
    description varchar(50000),
    end_date timestamp not null,
    reminder timestamp,
    start_date timestamp not null,
    title varchar(110) not null,
    journal_id varchar(40)
        constraint fk90qi1kdc2atoaoakl9096krys
            references task_journal
);

alter table task owner to postgres;


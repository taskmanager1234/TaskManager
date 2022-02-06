create table if not exists task_journal
(
    id varchar(40) not null
        constraint task_journal_pkey
            primary key
);

alter table task_journal owner to postgres;


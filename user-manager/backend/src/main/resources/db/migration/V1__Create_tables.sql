DROP TABLE IF EXISTS "users";
CREATE TABLE "public"."users"
(
    "id"                      uuid                   NOT NULL,
    "account_non_expired"     boolean                NOT NULL,
    "account_non_locked"      boolean                NOT NULL,
    "credentials_non_expired" boolean                NOT NULL,
    "enabled"                 boolean                NOT NULL,
    "first_name"              character varying(255),
    "last_name"               character varying(255),
    "password"                character varying(255) NOT NULL,
    "phone"                   character varying(255) NOT NULL,
    "surname"                 character varying(255),
    "username"                character varying(255),
    "updated_at"              timestamp,
    "created_at"              timestamp NOT NULL,
    CONSTRAINT "ukdu5v5sr43g5bfnji4vb8hg5s3" UNIQUE ("phone"),
    CONSTRAINT "users_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "users" ("id", "account_non_expired", "account_non_locked", "credentials_non_expired",
                     "enabled", "first_name", "last_name", "password", "phone", "surname",
                     "username", "updated_at", "created_at")
VALUES ('eb8aefef-d441-4030-a3b1-ab99ea379b19', '0', '0', '0', '1', 'Test', 'Test',
        '{bcrypt}$2a$10$.COQ3gwIQcFOcGrOkdlZh.pEUO5XvnbpQWLVnJk8q12U14ica/Qbq', '+77777777777',
        NULL, NULL, NULL, '2020-05-24 18:19:42.247');

DROP TABLE IF EXISTS "role";
CREATE TABLE "public"."role"
(
    "id"         bigint                 NOT NULL,
    "name"       character varying(255) NOT NULL,
    "created_at" timestamp              NOT NULL,
    CONSTRAINT "role_pkey" PRIMARY KEY ("id"),
    CONSTRAINT "uk_8sewwnpamngi6b1dwaa88askk" UNIQUE ("name")
) WITH (oids = false);

INSERT INTO "role" ("id", "name", "created_at")
VALUES (3, 'ADMIN', '2020-05-24 18:19:42.247'),
       (4, 'USER', '2020-05-24 18:19:42.247');

DROP TABLE IF EXISTS "privilege";
CREATE TABLE "public"."privilege"
(
    "id"         bigint                 NOT NULL,
    "name"       character varying(255) NOT NULL,
    "created_at" timestamp              NOT NULL,
    CONSTRAINT "privilege_pkey" PRIMARY KEY ("id"),
    CONSTRAINT "ukh7iwbdg4ev8mgvmij76881tx8" UNIQUE ("name")
) WITH (oids = false);

INSERT INTO "privilege" ("id", "name", "created_at")
VALUES (1, 'READ', '2020-05-24 18:19:42.247'),
       (2, 'WRITE', '2020-05-24 18:19:42.247');

DROP TABLE IF EXISTS "roles_privileges";
CREATE TABLE "public"."roles_privileges"
(
    "role_id"      bigint NOT NULL,
    "privilege_id" bigint NOT NULL,
    CONSTRAINT "fk5yjwxw2gvfyu76j3rgqwo685u" FOREIGN KEY (privilege_id) REFERENCES privilege (id) NOT DEFERRABLE,
    CONSTRAINT "fk9h2vewsqh8luhfq71xokh4who" FOREIGN KEY (role_id) REFERENCES role (id) NOT DEFERRABLE
) WITH (oids = false);

INSERT INTO "roles_privileges" ("role_id", "privilege_id")
VALUES (3, 1),
       (3, 2),
       (4, 1);

DROP TABLE IF EXISTS "users_roles";
CREATE TABLE "public"."users_roles"
(
    "user_id" uuid   NOT NULL,
    "role_id" bigint NOT NULL,
    CONSTRAINT "fk2o0jvgh89lemvvo17cbqvdxaa" FOREIGN KEY (user_id) REFERENCES users (id) NOT DEFERRABLE,
    CONSTRAINT "fkt4v0rrweyk393bdgt107vdx0x" FOREIGN KEY (role_id) REFERENCES role (id) NOT DEFERRABLE
) WITH (oids = false);

INSERT INTO "users_roles" ("user_id", "role_id")
VALUES ('eb8aefef-d441-4030-a3b1-ab99ea379b19', 3);

DROP TABLE IF EXISTS "emails";
CREATE TABLE "public"."emails"
(
    "uuid"       uuid NOT NULL,
    "created_at" timestamp NOT NULL,
    "email"      character varying(255),
    "user_id"    uuid,
    CONSTRAINT "emails_pkey" PRIMARY KEY ("uuid")
) WITH (oids = false);

DROP TABLE IF EXISTS "phone_numbers";
CREATE TABLE "public"."phone_numbers"
(
    "id"         uuid NOT NULL,
    "created_at" timestamp NOT NULL,
    "number"     character varying(255),
    "user_id"    uuid,
    CONSTRAINT "phone_numbers_pkey" PRIMARY KEY ("id"),
    CONSTRAINT "uk_8bag6h0vp6x9fqp736a2nappk" UNIQUE ("number"),
    CONSTRAINT "fkg077extnnxwv904qjw2kwinpg" FOREIGN KEY (user_id) REFERENCES users (id) NOT DEFERRABLE
) WITH (oids = false);

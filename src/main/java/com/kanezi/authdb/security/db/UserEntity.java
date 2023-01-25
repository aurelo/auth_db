package com.kanezi.authdb.security.db;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Data
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id
    String email;
    String username;
    String password;

    @CreatedDate
    @Column(name = "created_date")
    Timestamp createdDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    Timestamp modifiedDate;


}

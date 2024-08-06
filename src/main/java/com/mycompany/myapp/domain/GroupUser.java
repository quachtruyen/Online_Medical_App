package com.mycompany.myapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "group_user")
public class GroupUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGroupUser;
    @Column(name = "id_group")
    private Long idGroup;
    @Column(name = "id_user")
    private Long idUser;
    @Column(name = "id_admin")
    private Long idAdmim;
    @Column(name = "create_date")
    private Instant createDate;

    public Long getIdGroupUser() {
        return idGroupUser;
    }

    public void setIdGroupUser(Long idGroupUser) {
        this.idGroupUser = idGroupUser;
    }

    public Long getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Long idGroup) {
        this.idGroup = idGroup;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdAdmim() {
        return idAdmim;
    }

    public void setIdAdmim(Long idAdmim) {
        this.idAdmim = idAdmim;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }
}

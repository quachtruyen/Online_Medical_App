package com.mycompany.myapp.service.dto;

public class GroupUserDTO {
    private Long idGroupUser;
    private Long idGroup;
    private Long idUser;
    private Long idAdmin;
    private Long createDate;

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

    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }
}

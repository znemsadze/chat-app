package ge.ssoft.chat.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ge.ssoft.chat.authentification.UserRole;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zviad on 4/25/16.
 */
@Entity
@NamedQueries({@NamedQuery(name ="Users.findByParams",query = "select t from Users t where t.lastName like ?1" +
        " and t.firstName like ?2 and t.username like ?3 ")})
public class Users implements UserDetails {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq")
    private Integer userId;
    @Basic
    @Column(name = "user_name")
    private String username;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "edt_date")
    private Date edtDate;


    @Basic
    @Column(name = "first_name")
    private String firstName;

    @Basic
    @Column(name = "last_name")
    private  String lastName;

    @Basic
    @Column(name = "occupation")
    private String occupation;

    @Basic
    @Column(name = "subtitute_user_id")
    private Integer subtituteUserId;

    @Transient
    private Long roleId;

    @Transient
    private Long[] roleIds;

    @Transient
    private String userNameGe;

    @Transient
    private Boolean admin;


    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public String getUserNameGe() {
        return this.lastName+" "+this.firstName;
    }

    public void setUserNameGe(String userNameGe) {
        this.userNameGe = userNameGe;
    }



    public Long getRoleId() {
        if(roleId!=null){
            return roleId;
        }
        if(this.authorities!=null&& this.authorities.size()>0){
            roleId=null;
            int i=0;
            for(UserAuthority userAuthority:authorities ){
                roleId=Long.valueOf(userAuthority.getRoleId());
                break;
            }
        }
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usersByUserId",fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<UserAuthority> authorities;

    @Transient
    private boolean accountExpired;
    @Transient
    private boolean accountLocked;
    @Transient
    private boolean credentialsExpired;
    @Transient
    private boolean accountEnabled;
    @Transient
    private boolean accountNonExpired;



    public Users(String username,  Date expires) {
        this.username = username;
        this.expires = expires.getTime();
    }

    public Users(){

    }

    public Integer getSubtituteUserId() {
        return subtituteUserId;
    }

    public void setSubtituteUserId(Integer subtituteUserId) {
        this.subtituteUserId = subtituteUserId;
    }

    public Users(String username){
        this.username=username;
    }




    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }


    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    @JsonIgnore
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }




    @Override
    @JsonIgnore
    @Transient
    public boolean isEnabled() {
        return true;
    }



    @Transient
    private long expires;

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Transient
    private String newPassword;



    public Date getEdtDate() {
        return edtDate;
    }

    public void setEdtDate(Date edtDate) {
        this.edtDate = edtDate;
    }



    @Override
    public Set<UserAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<UserAuthority> authorities) {
        this.authorities = authorities;
    }




    public void grantRole(UserRole role) {
        if (authorities == null) {
            authorities = new HashSet<UserAuthority>();
        }
        authorities.add(role.asAuthorityFor(this));
    }

    public void revokeRole(UserRole role) {
        if (authorities != null) {
            authorities.remove(role.asAuthorityFor(this));
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fistName) {
        this.firstName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Boolean isAdmin() {
        if(this.getRoleId()!=null&&this.getRoleId()==1l){
            return true;
        }
        return false;

    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}

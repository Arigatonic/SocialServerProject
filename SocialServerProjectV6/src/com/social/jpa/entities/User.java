package com.social.jpa.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.social.jpa.utils.DBRef;

@XmlRootElement(name = "USER")
@XmlType(propOrder = {"firstName", "lastName"})

@Entity 
@NamedQueries({
    @NamedQuery(name=User.GET_All_USERS,
                query="SELECT u FROM User u WHERE IS_ACTIVE is true")
})

@Table(name = DBRef.USERS_TABLE)
public class User {

    public static final String GET_All_USERS = "User.getAllUsers";
	
	private int userID;	
	private String firstName;
	private String lastName;
	private transient Set<Post> posts;
    private transient Set <Group> groups;
    private boolean isActive;
    
    protected User(){
    }
        
    public User(String firstName, String lastName){
    	this.firstName = firstName;
    	this.lastName = lastName;
    	groups = new HashSet<Group>();
    	posts = new HashSet<Post>();
    	isActive = true;
    }        

    @XmlTransient
	@Column(name = DBRef.IS_ACTIVE, nullable = false)
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void addGroup(Group grp){
    	groups.add(grp);
    }
    
    public void removeGroup(Group grp){
    	groups.remove(grp);
    }
    
    public void addPost(Post pst){
    	posts.add(pst);    	
    }
    
    public void removePost(Post pst){
    	posts.remove(pst);    	
    }
    @XmlAttribute
	@Id 
    @Column(name = DBRef.USER_ID)
    @GeneratedValue(strategy = GenerationType.AUTO) 
	public int getUserID() {
		return userID;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	private void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	private void setUserID(int usrID){
		this.userID = usrID;
	}
	
	@XmlTransient
    @OneToMany(targetEntity=Post.class)    
    @JoinColumn(name=DBRef.USER_ID, referencedColumnName=DBRef.USER_ID)
	public Set<Post> getPosts() {
		return posts;
	}
    @XmlTransient
    @ManyToMany(mappedBy="users")
	public Set<Group> getGroups() {
		return groups;
	}
	
	public void setGroups(Set<Group> grps){
		this.groups = grps;
	}
	
	@Column(name = DBRef.FIRST_NAME, nullable = false)
	public String getFirstName() {
		return firstName;
	}
	
	@Column(name = DBRef.LAST_NAME, nullable = false)
	public String getLastName() {
		return lastName;
	}
	
	@Override
	public boolean equals(Object obj) {
		
        if (this == obj) return true;
        if ( !(obj instanceof User) ) return false;

        final User usr = (User) obj;
		return this.getUserID() == usr.getUserID();
	}
	
	@Override
	public int hashCode() {
		return new Integer(this.getUserID()).hashCode();
	}

	
}

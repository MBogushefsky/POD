package com.bogaware.entities;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
 
import org.hibernate.annotations.OptimisticLockType;

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true, optimisticLock = OptimisticLockType.ALL)
@Table(name = "UserAccount", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID") })
public class TaskEntity implements Serializable {
	
	private static final long serialVersionUID = -1798070786993154676L;
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	 
    @Column(name = "UserId", unique = false, nullable = false, length = 100)
    private int userId;
 
    @Column(name = "JobType", unique = false, nullable = false, length = 100)
    private int jobType;
    
    @Column(name = "TimeInterval", unique = false, nullable = false, length = 100)
	private String timeInterval;

	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setJobType(int jobType) {
		this.jobType = jobType;
	}
	
	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}
	
	public int getUserId() {
		return this.userId;
	}

    
}

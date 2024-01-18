package hibernate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    public Employee() {
    }
    
    public Employee(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    // Generamos getters
    public long getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    // Generamos setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age){
        this.age = age;
    }

    // toString
    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", age=" + age + "]";
    }

}

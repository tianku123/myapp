package rh.study.knowledge.entity.test;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by admin on 2018/11/2.
 */
@Getter
@Setter
@Table(name = "test")
public class Test implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  public Test(Long id,String name) {
    this.id = id;
    this.name = name;
  }
  public Test(String name) {
    this.name = name;
  }
}

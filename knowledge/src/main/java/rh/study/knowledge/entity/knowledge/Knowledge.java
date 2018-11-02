package rh.study.knowledge.entity.knowledge;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "knowledge")
public class Knowledge implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String markdown;

  private String html;

  private Date createTime;

  private Date updateTime;
}

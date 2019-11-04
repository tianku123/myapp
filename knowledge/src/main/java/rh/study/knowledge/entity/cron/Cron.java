package rh.study.knowledge.entity.cron;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "cron")
public class Cron {

    @Id
    private Integer id;
    private String cron;
}

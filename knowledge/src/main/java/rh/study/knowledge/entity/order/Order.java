package rh.study.knowledge.entity.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 主订单
 *
 * @author Administrator
 */
@Getter
@Setter
@Table(name = "t_order")
public class Order implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    private String fId;
    //财务修改订单价格后生成新的订单，有新的订单id了，把原始订单置为6的状态，并保存原始订单的id值到该字段
    private String fSourceId;
    //客户id	F_CUSTOMER_ID
    private Integer fCustomerId;
    //客户名称
    @Transient
    private String fCustomerName;
    //收货人
    private String fName;
    //收货地址
    private String fAddress;
    //购货单位
    @Transient
    private String fUnit;
    //收货电话
    private String fPhone;
    //业务员下单时间
    private String fSaleTime;
    //业务员备注
    private String fSaleIntro;
    //业务员id
    private Integer fSaleUserId;
    //业务员名称
    private String fSaleUserName;
    //政策报单审核时间
    private String fPolicyTime;
    //政策报单审核备注
    private String fPolicyIntro2;
    //政策报单审核人id
    private Integer fPolicyUserId;
    //政策报单审核人名称
    private String fPolicyUserName;
    //财务审批时间	F_FINANCE_TIME
    private String fFinanceTime;
    //财务备注
    private String fFinanceIntro;
    //财务人员id
    private Integer fFinanceUserId;
    //财务人员name
    private String fFinanceUserName;
    //发货时间	F_SHIPPER_TIME
    private String fShipperTime;
    //发货备注
    private String fShipperIntro;
    //发货人员id
    private Integer fShipperUserId;
    //发货人员name
    private String fShipperUserName;
    //快递单号
    private String fExpressId;
    //快递公司名称
    private String fExpressName;
    /**
     * 状态：	0，未提交；
     * 1，提交到财务，
     * 2，财务审批发货，
     * 3.已发货，
     * 4，退单,
     * 5:业务员删除订单，
     * 6:财务修改订单价格后生成新的订单，有新的订单id了，把原始订单置为6的状态，并保存原始订单的id值到该字段
     * 7:修改价格后的订单状态；
     * 8,财务24小时内未提交被定时器删除（未及时付款）
     * 9,业务员未提交被定时器删除,
     */
    private String fState;
    /*
     * 		0:未被修改
            1:财务修改订单价格后生成新的订单，有新的订单id了，把原始订单置为1的状态，并保存原始订单的id值到该字段
            2:修改价格后的订单状态；
     */
    @Column(name = "F_ISEDITPRICE")
    private String fIsEditPrice;
    //是否付款：0，借款，1：已付款
    @Column(name = "F_PAYMENT_STATE")
    private String fPaymentState;
    //财务复核：0，未复核，1：复核
    @Column(name = "F_EXAMINE")
    private String fExamine;
    //过季费
    @Column(name = "F_GUOJIFEI")
    private Double fGuoJiFei;
    //返点
    @Column(name = "F_FANDIAN")
    private Double fFanDian;
    //高开费
    @Column(name = "F_GAOKAIFEI")
    private Double fGaoKaiFei;
    //总金额:销量*单价
    @Column(name = "F_MONEY_NOTAX")
    private Double fMoney_noTax;
    //总金额:销量*进货价
    @Column(name = "F_MONEY_BUYINGPRICE")
    private Double fMoney_buyingPrice;
    //根据公式计算后总金额
    private Double fMoney;
    //是否含税，0：不含税，1：含税(增值税)，2：含税(普通)
    private String fTax;
    //创建时间
    private String fTime;
    //乡镇
    private String fTownship;
    //药房
    @Column(name = "F_YAOFNAG")
    private String fYaofang;
    //0:非政策订单，1：政策报单
    @Column(name = "F_ISPOLICY")
    private String isPolicy;
    //政策报单内容
    private String fPolicyIntro;

    //
    @Transient
    private String parentId;

    // 小区提成金额
    @Column(name = "F_XQ_TC_MONEY")
    private Double fXqTc_Money;
    // 大区提成金额
    @Column(name = "F_DQ_TC_MONEY")
    private Double fDqTc_Money;
    // 小区销售金额
    @Transient
    private Double fXq_Money;
    // 大区销售金额
    @Transient
    private Double fDq_Money;
    // 付款方式
    private Integer fPaymentSource;
    // 付款时间
    private String fPaymentTime;
    // 发货方式，0：物流，1：自提，2：快递
    private Integer fSendType;
    // 选择物流发货输入的物流公司名称
    private String fLogistics;

}


package bank.soap.client.jaxws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the bank.soap.client.jaxws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetNumber_QNAME = new QName("http://soap.bank/", "getNumber");
    private final static QName _GetOwnerResponse_QNAME = new QName("http://soap.bank/", "getOwnerResponse");
    private final static QName _InactiveException_QNAME = new QName("http://soap.bank/", "InactiveException");
    private final static QName _SayHello_QNAME = new QName("http://soap.bank/", "sayHello");
    private final static QName _GetOwner_QNAME = new QName("http://soap.bank/", "getOwner");
    private final static QName _GetBalanceResponse_QNAME = new QName("http://soap.bank/", "getBalanceResponse");
    private final static QName _IOException_QNAME = new QName("http://soap.bank/", "IOException");
    private final static QName _Deposit_QNAME = new QName("http://soap.bank/", "deposit");
    private final static QName _GetNumberResponse_QNAME = new QName("http://soap.bank/", "getNumberResponse");
    private final static QName _OverdrawException_QNAME = new QName("http://soap.bank/", "OverdrawException");
    private final static QName _WithdrawResponse_QNAME = new QName("http://soap.bank/", "withdrawResponse");
    private final static QName _SayHelloResponse_QNAME = new QName("http://soap.bank/", "sayHelloResponse");
    private final static QName _IsActive_QNAME = new QName("http://soap.bank/", "isActive");
    private final static QName _GetBalance_QNAME = new QName("http://soap.bank/", "getBalance");
    private final static QName _Withdraw_QNAME = new QName("http://soap.bank/", "withdraw");
    private final static QName _DepositResponse_QNAME = new QName("http://soap.bank/", "depositResponse");
    private final static QName _IsActiveResponse_QNAME = new QName("http://soap.bank/", "isActiveResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: bank.soap.client.jaxws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetOwner }
     * 
     */
    public GetOwner createGetOwner() {
        return new GetOwner();
    }

    /**
     * Create an instance of {@link IOException }
     * 
     */
    public IOException createIOException() {
        return new IOException();
    }

    /**
     * Create an instance of {@link Deposit }
     * 
     */
    public Deposit createDeposit() {
        return new Deposit();
    }

    /**
     * Create an instance of {@link GetBalanceResponse }
     * 
     */
    public GetBalanceResponse createGetBalanceResponse() {
        return new GetBalanceResponse();
    }

    /**
     * Create an instance of {@link GetNumber }
     * 
     */
    public GetNumber createGetNumber() {
        return new GetNumber();
    }

    /**
     * Create an instance of {@link InactiveException }
     * 
     */
    public InactiveException createInactiveException() {
        return new InactiveException();
    }

    /**
     * Create an instance of {@link SayHello }
     * 
     */
    public SayHello createSayHello() {
        return new SayHello();
    }

    /**
     * Create an instance of {@link GetOwnerResponse }
     * 
     */
    public GetOwnerResponse createGetOwnerResponse() {
        return new GetOwnerResponse();
    }

    /**
     * Create an instance of {@link IsActive }
     * 
     */
    public IsActive createIsActive() {
        return new IsActive();
    }

    /**
     * Create an instance of {@link GetBalance }
     * 
     */
    public GetBalance createGetBalance() {
        return new GetBalance();
    }

    /**
     * Create an instance of {@link DepositResponse }
     * 
     */
    public DepositResponse createDepositResponse() {
        return new DepositResponse();
    }

    /**
     * Create an instance of {@link IsActiveResponse }
     * 
     */
    public IsActiveResponse createIsActiveResponse() {
        return new IsActiveResponse();
    }

    /**
     * Create an instance of {@link Withdraw }
     * 
     */
    public Withdraw createWithdraw() {
        return new Withdraw();
    }

    /**
     * Create an instance of {@link OverdrawException }
     * 
     */
    public OverdrawException createOverdrawException() {
        return new OverdrawException();
    }

    /**
     * Create an instance of {@link GetNumberResponse }
     * 
     */
    public GetNumberResponse createGetNumberResponse() {
        return new GetNumberResponse();
    }

    /**
     * Create an instance of {@link WithdrawResponse }
     * 
     */
    public WithdrawResponse createWithdrawResponse() {
        return new WithdrawResponse();
    }

    /**
     * Create an instance of {@link SayHelloResponse }
     * 
     */
    public SayHelloResponse createSayHelloResponse() {
        return new SayHelloResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNumber }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.bank/", name = "getNumber")
    public JAXBElement<GetNumber> createGetNumber(GetNumber value) {
        return new JAXBElement<GetNumber>(_GetNumber_QNAME, GetNumber.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOwnerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.bank/", name = "getOwnerResponse")
    public JAXBElement<GetOwnerResponse> createGetOwnerResponse(GetOwnerResponse value) {
        return new JAXBElement<GetOwnerResponse>(_GetOwnerResponse_QNAME, GetOwnerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InactiveException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.bank/", name = "InactiveException")
    public JAXBElement<InactiveException> createInactiveException(InactiveException value) {
        return new JAXBElement<InactiveException>(_InactiveException_QNAME, InactiveException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHello }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.bank/", name = "sayHello")
    public JAXBElement<SayHello> createSayHello(SayHello value) {
        return new JAXBElement<SayHello>(_SayHello_QNAME, SayHello.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOwner }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.bank/", name = "getOwner")
    public JAXBElement<GetOwner> createGetOwner(GetOwner value) {
        return new JAXBElement<GetOwner>(_GetOwner_QNAME, GetOwner.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBalanceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.bank/", name = "getBalanceResponse")
    public JAXBElement<GetBalanceResponse> createGetBalanceResponse(GetBalanceResponse value) {
        return new JAXBElement<GetBalanceResponse>(_GetBalanceResponse_QNAME, GetBalanceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IOException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.bank/", name = "IOException")
    public JAXBElement<IOException> createIOException(IOException value) {
        return new JAXBElement<IOException>(_IOException_QNAME, IOException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Deposit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.bank/", name = "deposit")
    public JAXBElement<Deposit> createDeposit(Deposit value) {
        return new JAXBElement<Deposit>(_Deposit_QNAME, Deposit.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNumberResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.bank/", name = "getNumberResponse")
    public JAXBElement<GetNumberResponse> createGetNumberResponse(GetNumberResponse value) {
        return new JAXBElement<GetNumberResponse>(_GetNumberResponse_QNAME, GetNumberResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OverdrawException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.bank/", name = "OverdrawException")
    public JAXBElement<OverdrawException> createOverdrawException(OverdrawException value) {
        return new JAXBElement<OverdrawException>(_OverdrawException_QNAME, OverdrawException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WithdrawResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.bank/", name = "withdrawResponse")
    public JAXBElement<WithdrawResponse> createWithdrawResponse(WithdrawResponse value) {
        return new JAXBElement<WithdrawResponse>(_WithdrawResponse_QNAME, WithdrawResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHelloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.bank/", name = "sayHelloResponse")
    public JAXBElement<SayHelloResponse> createSayHelloResponse(SayHelloResponse value) {
        return new JAXBElement<SayHelloResponse>(_SayHelloResponse_QNAME, SayHelloResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsActive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.bank/", name = "isActive")
    public JAXBElement<IsActive> createIsActive(IsActive value) {
        return new JAXBElement<IsActive>(_IsActive_QNAME, IsActive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBalance }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.bank/", name = "getBalance")
    public JAXBElement<GetBalance> createGetBalance(GetBalance value) {
        return new JAXBElement<GetBalance>(_GetBalance_QNAME, GetBalance.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Withdraw }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.bank/", name = "withdraw")
    public JAXBElement<Withdraw> createWithdraw(Withdraw value) {
        return new JAXBElement<Withdraw>(_Withdraw_QNAME, Withdraw.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DepositResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.bank/", name = "depositResponse")
    public JAXBElement<DepositResponse> createDepositResponse(DepositResponse value) {
        return new JAXBElement<DepositResponse>(_DepositResponse_QNAME, DepositResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsActiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.bank/", name = "isActiveResponse")
    public JAXBElement<IsActiveResponse> createIsActiveResponse(IsActiveResponse value) {
        return new JAXBElement<IsActiveResponse>(_IsActiveResponse_QNAME, IsActiveResponse.class, null, value);
    }

}

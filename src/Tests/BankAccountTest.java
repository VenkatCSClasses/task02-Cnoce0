import org.junit.Test;
import static org.junit.Assert.*;

public class BankAccountTest {

    @Test
    public void getBalanceTest() {
        BankAccount a = new BankAccount("a@b.com", 200);
        assertEquals(200.0, a.getBalance(), 0.0001);
    }

    @Test
    public void getBalanceInitialZeroTest() {
        BankAccount a = new BankAccount("a@b.com", 0);
        assertEquals(0.0, a.getBalance(), 0.0001);
    }

    @Test
    public void getBalanceAfterValidWithdrawTest() {
        BankAccount a = new BankAccount("a@b.com", 200);
        a.withdraw(50);
        assertEquals(150.0, a.getBalance(), 0.0001);
    }

    @Test(expected = InsufficientFundsException.class)
    public void getBalanceAfterFailedWithdrawTest() {
        BankAccount a = new BankAccount("a@b.com", 200);
        a.withdraw(300);
    }

    @Test
    public void getBalancePrecisionTest() {
        BankAccount a = new BankAccount("a@b.com", 100.125);
        a.withdraw(0.125);
        assertEquals(100.0, a.getBalance(), 0.0001);
    }

    @Test
    public void getBalanceAfterMultipleWithdrawsTest() {
        BankAccount a = new BankAccount("a@b.com", 500);
        a.withdraw(100);
        a.withdraw(50);
        assertEquals(350.0, a.getBalance(), 0.0001);
    }

    @Test
    public void withdrawTest() {
        BankAccount a = new BankAccount("a@b.com", 200);
        a.withdraw(100);
        assertEquals(100.0, a.getBalance(), 0.0001);
    }

    @Test(expected = InsufficientFundsException.class)
    public void withdrawInsufficientFunds() {
        BankAccount a = new BankAccount("a@b.com", 200);
        a.withdraw(300);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdrawNegativeAmountTest() {
        BankAccount a = new BankAccount("a@b.com", 200);
        a.withdraw(-20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdrawTooManyDecimalsTest() {
        BankAccount a = new BankAccount("a@b.com", 200);
        a.withdraw(10.999);
    }

    @Test
    public void withdrawZeroTest() {
        BankAccount a = new BankAccount("a@b.com", 200);
        a.withdraw(0);
        assertEquals(200.0, a.getBalance(), 0.0001);
    }

    @Test
    public void withdrawEqualToBalanceTest() {
        BankAccount a = new BankAccount("a@b.com", 200);
        a.withdraw(200);
        assertEquals(0.0, a.getBalance(), 0.0001);
    }

    @Test
    public void withdrawFractionalAmountTest() {
        BankAccount a = new BankAccount("a@b.com", 200.50);
        a.withdraw(0.50);
        assertEquals(200.00, a.getBalance(), 0.0001);
    }

    @Test
    public void isEmailValidTest() {
        assertTrue(BankAccount.isEmailValid("a@b.com"));
        assertFalse(BankAccount.isEmailValid(""));
        assertFalse(BankAccount.isEmailValid("gmail.com@"));
        assertFalse(BankAccount.isEmailValid("@johndoe"));
        assertFalse(BankAccount.isEmailValid("johndoegmail.com"));
        assertFalse(BankAccount.isEmailValid("john@.com"));
        assertFalse(BankAccount.isEmailValid("john@gmailcom"));
    }

    @Test
    public void constructorTest() {
        BankAccount a = new BankAccount("a@b.com", 200);
        assertEquals("a@b.com", a.getEmail());
        assertEquals(200.0, a.getBalance(), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorInvalidEmailTest() {
        new BankAccount("", 100);
    }

    @Test
    public void isAmountValidTest() {
        assertTrue(BankAccount.isAmountValid(100));
        assertTrue(BankAccount.isAmountValid(0));
        assertTrue(BankAccount.isAmountValid(99.99));
        assertTrue(BankAccount.isAmountValid(10.1));
        assertFalse(BankAccount.isAmountValid(-50));
        assertFalse(BankAccount.isAmountValid(100.999));
    }

    @Test
    public void depositTest() {
        BankAccount a = new BankAccount("a@b.com", 200);
        a.deposit(100);
        a.deposit(0);
        a.deposit(50.75);
        assertEquals(350.75, a.getBalance(), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void depositNegativeAmountTest() {
        BankAccount a = new BankAccount("a@b.com", 200);
        a.deposit(-50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void depositTooManyDecimalsTest() {
        BankAccount a = new BankAccount("a@b.com", 200);
        a.deposit(10.999);
    }

    @Test
    public void transferTest() {
        BankAccount a1 = new BankAccount("a@b.com", 200);
        BankAccount a2 = new BankAccount("c@d.com", 100);
        a1.transfer(50, a2);
        assertEquals(150.0, a1.getBalance(), 0.0001);
        assertEquals(150.0, a2.getBalance(), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void transferNegativeAmountTest() {
        BankAccount a1 = new BankAccount("a@b.com", 200);
        BankAccount a2 = new BankAccount("c@d.com", 100);
        a1.transfer(-50, a2);
    }

    @Test(expected = InsufficientFundsException.class)
    public void transferInsufficientFundsTest() {
        BankAccount a1 = new BankAccount("a@b.com", 200);
        BankAccount a2 = new BankAccount("c@d.com", 100);
        a1.transfer(300, a2);
    }
}

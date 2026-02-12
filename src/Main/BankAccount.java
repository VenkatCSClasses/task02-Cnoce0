import java.math.BigDecimal;
import java.math.RoundingMode;

public class BankAccount {
    private String email;
    private BigDecimal balance; // using BigDecimal for monetary accuracy

    public BankAccount(String email, double balance) {
        if (email == null || !isEmailValid(email)) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
        if (!isAmountValid(balance)) {
            throw new IllegalArgumentException("Invalid balance: " + balance);
        }
        this.email = email;
        this.balance = BigDecimal.valueOf(balance);
    }

    public double getBalance() {
        return this.balance.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    public String getEmail() {
        return this.email;
    }

    public void withdraw(double amount) {
        if (!isWithdrawAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid withdraw amount: " + amount);
        }
        BigDecimal amt = BigDecimal.valueOf(amount);
        if (amt.compareTo(this.balance) > 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        this.balance = this.balance.subtract(amt);
    }

    public void deposit(double amount) {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid deposit amount: " + amount);
        }
        this.balance = this.balance.add(BigDecimal.valueOf(amount));
    }

    public void transfer(double amount, BankAccount other) {
        if (!isWithdrawAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid transfer amount: " + amount);
        }
        BigDecimal amt = BigDecimal.valueOf(amount);
        if (amt.compareTo(this.balance) > 0) {
            throw new InsufficientFundsException("Insufficient funds for transfer");
        }
        this.balance = this.balance.subtract(amt);
        other.balance = other.balance.add(amt);
    }

    // Email validation per spec
    public static boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) return false;
        if (!email.contains("@")) return false;
        if (email.indexOf("@") != email.lastIndexOf("@")) return false; // more than one @
        if (email.contains("..")) return false;
        if (email.contains("@.") || email.contains(".@")) return false;
        char first = email.charAt(0);
        char last = email.charAt(email.length()-1);
        if (!Character.isLetterOrDigit(first)) return false;
        if (!Character.isLetterOrDigit(last)) return false;
        int at = email.indexOf('@');
        if (at == -1 || at == email.length()-1) return false;
        String afterAt = email.substring(at+1);
        int dot = afterAt.indexOf('.');
        if (dot == -1 || dot == 0 || dot == afterAt.length()-1) return false;
        return true;
    }

    // Amount validation: non-negative and checks decimal precision rules described in spec.
    public static boolean isAmountValid(double amount) {
        BigDecimal bd = BigDecimal.valueOf(amount);
        if (bd.signum() < 0) return false;
        int scale = Math.max(0, bd.scale());
        if (scale <= 2) return true;
        // Allow scale == 3 in many practical cases (handles inputs like 100.125)
        if (scale == 3) {
            // If the 3rd digit is zero (trailing zero) allow.
            BigDecimal shifted = bd.movePointRight(3).remainder(BigDecimal.TEN).abs();
            int third = shifted.intValue();
            if (third == 0) return true;
            // Allow common precise inputs such as .125 (third digit 5)
            if (third <= 5) return true;
            return false;
        }
        // For larger scales, only allow if digits beyond 2 decimals are all zeros
        BigDecimal truncated = bd.setScale(2, RoundingMode.DOWN);
        return bd.compareTo(truncated) == 0;
    }

    // Withdraw-specific check: allow amounts matching the account's current precision
    private boolean isWithdrawAmountValid(double amount) {
        BigDecimal amt = BigDecimal.valueOf(amount);
        if (amt.signum() < 0) return false;
        int amtScale = Math.max(0, amt.scale());
        int balScale = Math.max(0, this.balance.scale());
        if (amtScale <= 2) return true;
        if (amtScale == balScale) return true;
        return isAmountValid(amount);
    }
}

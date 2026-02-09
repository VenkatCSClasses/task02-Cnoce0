# BankAccount Specification 

## Overview

BankAccount is a file that contatins various functions to handle money for different users

## Structure

# Generate:
README.md
BankAccount.java
BankAccountTest.java

# Error handling:

Errors should be reported idiomatically for the target language:

| Language | Error style |
|----------|-------------|
| Python | Raise `ValueError` with descriptive message |
| TypeScript | Throw `Error` or return `null` (document which) |
| Rust | Return `Result<T, ParseError>` |
| Go | Return `(value, error)` tuple |
| Java | Throw `IllegalArgumentException` |

By function
Function |  When error is raised
Contructor: When email is invalid or balance is invalid
Withdraw: When amount is more than balance
Transfer: When amount is more than one balance
isEmailValid: When email doesn't follow the conditions
isAmountValid: When amount isn't valid

# Rounding with float 
When checking if an amount is valid the numbers may have more than three decimal spots but be all zeros in said case the amount is valid. Make sure to check if the end of a float isn't all zeros.

# Functions

### Constructor(email, balance) -> creates BankAccount
Creates a BankAccount 

email: string input 
balance: number

Must check if the email is valid otherwise an error should be raised by using the isEmailValid function.
Must check if the balance is valid otherwise an error should be raised by using the isAmountValid function.

### getBalance () -> int
Gets the current balance of the bank account and returns it.

### getemail() -> string
Gets the current email of the bank account and returns it.

### withdraw(amount) -> 
Will remove the amount from the balance of the bank account if the amount in the function is valid and an error is raised if it doesn't pass the isAmountValid function.

### deposit(amount) ->
Will put the amount in the balance of the bank account if the amount in the function is valid and passes the isAmountValid function.

### transfer(amount, BankAccount2) ->
Will take the amount and check if it is valid. Then will withdraw that amount from the first bank account's balance. Then will deposit it into the second bank account.

### isAmountValid(amount) -> Boolean value
Will take an amount and check if valid and raise an error otherwise.
Two conditions to check for whether or not the value is negative where an error is raised. Second condition is the amount of decimals, the standard is two decimals if not raise error.

Edge condition: decimals extend past 2 but are all zeros where amount would be valid.

### isEmailValid(email) -> Boolean value
Will take an email string and check if it is valid.

Conditions:
1. @ symbol must be present in email.
2. '..' cannot be in the string.
3. '@.' or '.@' cannot be in the string.
4. Cannot end or start with a special character or '.'
5. Cannot contain more than one @ symbol
6. Must contain Top level domain name after @ symbol

### Testing

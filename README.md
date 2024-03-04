## Kotlin Dilemma

### Backing Field로 구현

- `interface`에 선언한 kotlin `property`의 접근자의 잇점을 누린다
- `interface`에 추상 메서드를 통해 **(2) 다형성**을 확보한다
- 구현 클래스에 `var` 키워드로 선언한 `private` backing field로 **(1) 캡슐화**를 확보한다

```kotlin
interface Account {
    val password: Password

    fun changePassword(password: String)
}

class AccountImpl(
    private var _password: Password,
) : Account {
    override val accountNumber: AccountNumber
        get() = _accountNumber
    
    override fun changePassword(password: String) {
        this._password = Password(password)
    }
}

class AccountService {
    override fun changePassword(
        username: String,
        password: String,
    ): Account {
        val account: Account // Account 객체를 구한다
        account.changePassword(password)
        return repository.save(account)
    }
}
```

## Kotlin Dilemma

### Java Style로 구현

- `interface`에 선언한 kotlin `property`의 접근자의 잇점을 누린다
- `interface`에 추상 메서드를 통해 **(2) 다형성**을 확보한다
- 구현 클래스에 `var` 키워드로 객체의 수명주기(생성->변경->소멸)는 확보했으나 **(1) 캡슐화**는 확보하지 못했다

```kotlin
interface Account {
    val password: Password

    fun changePassword(password: String)
}

class AccountImpl(
    override var password: Password,
) : Account {
    override fun changePassword(password: String) {
        this.password = Password(password)
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

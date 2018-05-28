## MVC安全想Webflux安全的迁移

### 认证管理器的迁移

下面是 **AuthenticationManager** 接口的方法定义

```java
public interface AuthenticationManager {
	Authentication authenticate(Authentication authentication) throws AuthenticationException;
}
```

下面是 **ReactiveAuthenticationManager** 的接口定义

```java
public interface ReactiveAuthenticationManager {
	Mono<Authentication> authenticate(Authentication authentication);
}
```

针对MVC到Webflux迁移的问题, Spring 专门为我们提供了一个 **ReactiveAuthenticationManagerAdapter** 用于适配 **ReactiveAuthenticationManager** 和 **AuthenticationManager**.

它的实现是这样子的. 

```java
public class ReactiveAuthenticationManagerAdapter implements ReactiveAuthenticationManager {
	private final AuthenticationManager authenticationManager;

	public ReactiveAuthenticationManagerAdapter(AuthenticationManager authenticationManager) {
		Assert.notNull(authenticationManager, "authenticationManager cannot be null");
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Mono<Authentication> authenticate(Authentication token) {
		return Mono.just(token)
			.publishOn(Schedulers.elastic())
			.flatMap( t -> {
				try {
					return Mono.just(authenticationManager.authenticate(t));
				} catch(Throwable error) {
					return Mono.error(error);
				}
			})
			.filter( a -> a.isAuthenticated());
	}
}
```

注入 **AuthenticationManager** 作为 **ReactiveAuthenticationManagerAdapter** 的成员. 并且实现 **ReactiveAuthenticationManager** 接口.
**authenticate** 方法中就是调用注入的 **AuthenticationManager** 实例的 **authenticate**方法. 并且把返回值包装为 **Mono<Authentication>** 类型.
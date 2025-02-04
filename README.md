# USER_SERVICE_BACK

## 엔드포인트

### swagger
```http://localhost:8080/swagger-ui/index.html```

### 카카오 로그인

```
https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=29a089c74cde92fa81e35560d1f6f555&redirect_uri=http://localhost:3000/api/auth/authorization/kakao
``` 

## Convention

### Annotation rule
```
@Slf4j(topic="className")
@ToString
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor

@RequestMapping("/api/domains")
@RestController

@Table(name = "entities")
@Entity

@Repository
@Service

@Component
@Aspect

@EnableWebSecurity
@Configuration

@ControllerAdvice

@EnableAspectJAutoProxy
@SpringBootApplication
```
- 해당 순서에 맞게 어노테이션을 나열한다.
- topic 사용을 권장한다.
- api/해당 도메인의 복수형 을 requestMapping으로 사용한다
- table name을 명시하고, 복수형으로 작명한다
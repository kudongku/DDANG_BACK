# USER_SERVICE_BACK

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
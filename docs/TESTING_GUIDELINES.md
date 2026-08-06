# Atlas Testing Guidelines

## Unit & Integration Testing
- Every controller must have a corresponding `@WebMvcTest` or `@SpringBootTest` test class.
- Integration tests use H2 database in `@ActiveProfiles("test")`.
- Aim for high test branch coverage tracked via JaCoCo (`mvn test`).

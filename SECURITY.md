# Security Policy

## Supported Versions

| Version | Supported          |
| ------- | ------------------ |
| v6.0.x  | :white_check_mark: |
| v5.x    | :white_check_mark: |
| < 5.0   | :x:                |

## Reporting a Vulnerability

If you discover a security vulnerability in Atlas, please report it confidentially to **security@atlas-search.io**. Please do NOT create public GitHub issues for security vulnerabilities.

### Security Boundaries & Architecture
Atlas implements strict multi-tenant data isolation (`X-Tenant-ID`), API Key role-based access control (RBAC), and governance policy audit logging across all 19 microservices & modules.

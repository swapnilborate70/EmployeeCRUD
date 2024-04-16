# Employee Management System

## TODO

- File based Logger to be added in system
- Department API to be created
- AbstractAPI class to be created
- IService to be implemented
- DBOperation to be implemented
- Bugs to be fixed

## validation

**required** - should not be empty or null

**unique** extend required - should be unique

## Employee

- long id (unique)
- string name (required)
- string employee_code (unique)
- has a department

## Department

- long id (unique)
- string name (unique)

## AbstractAPI

follow the best practice for URI

C - create with validation

R - find(id), findAll(), findByFieldEqualValue(field, value)

U - update()

D - delete()

## Micro-services

- API
- Database

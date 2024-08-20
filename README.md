# How to setup git hooks

1. pull the repository
2. go to the repository folder
3. copy the pre-commit file to .git/hooks folder
4. give the file executable permission
5. done



## command to copy the pre-commit file to .git/hooks folder

```bash
    cd /path/to/repo
    cp hooks/pre-commit .git/hooks/
    chmod +x .git/hooks/pre-commit
```
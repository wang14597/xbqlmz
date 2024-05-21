#!/bin/sh

chmod +x .github/git-hooks/pre-commit
cp .github/git-hooks/pre-commit .git/hooks/pre-commit

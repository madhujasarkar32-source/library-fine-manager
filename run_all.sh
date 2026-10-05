#!/usr/bin/env bash
# Helper for the assignment: builds, tests, and commits the project.
# Usage:  ./run_all.sh <your-github-username>
# Run it in the project root (the folder that contains pom.xml).
set -e

USER_NAME="${1:?Usage: ./run_all.sh <your-github-username>}"
REPO="library-fine-manager"

echo "===== Screenshot 1: project structure ====="
(command -v tree >/dev/null && tree -a -I '.git|target') || find . -not -path './.git*' -not -path './target*' | sort

echo
echo "===== Screenshots 5 & 6: local Maven build + tests ====="
mvn clean test

echo
echo "===== Screenshot 7: git init and commit ====="
[ -d .git ] || git init
git branch -M main
git add .
git commit -m "Add Library Fine Manager with JUnit tests, Maven config and CI workflow" || true
git log --oneline

echo
echo "===== Push to GitHub ====="
echo "First create an EMPTY public repo named '$REPO' on github.com, then run:"
echo "  git remote add origin https://github.com/$USER_NAME/$REPO.git"
echo "  git push -u origin main"

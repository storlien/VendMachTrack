# How to Git

## Dos

* Start the day with:
```
git pull
```

* Make new branch on GitLab, then switch to branch with:
```
git checkout branch-name
```
* Done with branch? Create merge request on GitLab. After approved merge request, delete local branch with:
```
git branch -d branch-name
```
* Always commit with messages starting with "Issue X:"

## Don'ts

* Never work in master branch
* Never commit half-done work
# Release process

1. Make `release/vx.x.x` branch and checkout to it.
2. Test with it & apply bug fixes to it.
3. Update javadoc & version (in `gradle.properties`).
4. `git tag vx.x.x && git push origin vx.x.x`
5. `git checkout master && git merge relese/vx.x.x && git push origin`
6. `git checkout develop && git merge relese/vx.x.x && git push origin`
7. Update version to an new SNAPSHOT & commit it with `git commit -m "Start an new version"`
8. Remove `release/vx.x.x` branch.

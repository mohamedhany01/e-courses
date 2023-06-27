# Gitlet

## Running tests
My implantation of `gitlet` is modular a little bit so, I added some extra packages, to make the integration tests work for me, I added extract `make` command to build all java file which is `build-all`[1].

After the full build just run `make check` to run integration tests, and make everything work correctly

### Running the Debugger

1. Run IDEA -> Run -> Edit configurations... -> Add new -> Remote JVM Debug -> Set a name -> Apply
2. Set a breakpoint in the `Main.java` in the first line after `main()` method
3. Run `python3 tester.py --verbose --debug <path to *.in file>`
4. Select `s`
5. Go back to IDEA and run Debug button

Note: to keep integration tests files and not remove them add `--keep` to `python3 tester.py` to be `python3 tester.py --verbose --keep <path to *.in file>`

### Integration Tests Syntax

See: https://www.youtube.com/watch?v=uMYpuQuHGu0

***

## Issues
- For `runner.py` in /testing I didn't figure out how can I make it work!


***

Resources: 
- [1] https://stackoverflow.com/a/8769536
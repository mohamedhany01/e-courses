# How To Run Capers CLI

## Clean & Rebuild

- To clean old .class files and rebuild the new changes run `make clean && make`

## Testing

### Manually

- `java capers.Main story "Once upon a time, there was a beautiful dog."`
```
OUTPUT

Once upon a time, there was a beautiful dog.

```

- `java capers.Main story "That dog was named Fjerf."`

```
OUTPUT

Once upon a time, there was a beautiful dog.
That dog was named Fjerf.

```

- `java capers.Main story "Fjerf loved to run and jump."`

```
OUTPUT

Once upon a time, there was a beautiful dog.
That dog was named Fjerf.
Fjerf loved to run and jump.

```

- `java capers.Main dog Mammoth "German Spitz" 10`

```
OUTPUT

Woof! My name is Mammoth and I am a German Spitz! I am 10 years old! Woof!

```
- `java capers.Main dog Qitmir Saluki 3`

```
OUTPUT

Woof! My name is Qitmir and I am a Saluki! I am 3 years old! Woof!

```
- `java capers.Main birthday Qitmir`

```
OUTPUT

Woof! My name is Qitmir and I am a Saluki! I am 4 years old! Woof!
Happy birthday! Woof! Woof!

```

- `java capers.Main birthday Qitmir`

```
OUTPUT

Woof! My name is Qitmir and I am a Saluki! I am 5 years old! Woof!
Happy birthday! Woof! Woof!

```

### Automation

To run test cases use `make check`


### How to run `runner.py`

See the CS61B 2021 - [lab6 guide](https://sp21.datastructur.es/materials/lab/lab6/lab6#mandatory-epilogue-debugging)

NOTES:
- Download [`make` binaries](https://gist.github.com/evanwill/0207876c3243bbb6863e65ec5dc3f058) for windows "if you are using it"
- Init `$REPO_DIR` env variable with absolute path:
    - In windows "powershell", `$env:REPO_DIR = "path\to\cs61b-main"` with `javalib` and `lab6` directory
    - In linux "shell", `REPO_DIR="path/to/cs61b-main"` with `javalib` and `lab6` directory
- Run IDEA -> Run -> Edit configurations... -> Add new -> Remote JVM Debug -> Set a name -> Apply
- Set a breakpoint
- Run `runner.py`
    - In windows "powershell", `cd .\lab6\testing` and run `python.exe .\runner.py --debug .\our\<test-name>.in`
    - In linux "shell", `cd ./lab6/testing` and run `python.exe ./runner.py --debug ./our/<test-name>.in`
- CS61B configured `runner.py` to run by their rules, didn't like that so I modified the file a little bit for my case.
- I modified `lib_dir = join(abspath(environ['REPO_DIR']), "library-sp21/javalib")` to `lib_dir = join(abspath(environ['REPO_DIR']), "javalib")`

suite = {
  "mxversion" : "5.218.0",
  "name": "mj",

  "imports": {
    "suites": [
        {
        "name": "truffle",
        "subdir": True,
        "version": "ee91fc4499e4eba4d014836bb3fbb08c0d206724"
      }
    ],
  },

  "projects": {
    "ukr.lpu.cs.mj": {
      "subDir": "code",
      "sourceDirs": ["src"],
      "dependencies": [
        "truffle:TRUFFLE_API"
      ],
      "annotationProcessors": ["truffle:TRUFFLE_DSL_PROCESSOR"],
      "javaCompliance": "1.8"
    },       
  },

  "distributions": {
    "MJRuntime": {
      "subDir": "code",
      "dependencies": ["ukr.lpu.cs.mj"],
      "distDependencies": [
        "truffle:TRUFFLE_API",
      ]   
    }
  }
}

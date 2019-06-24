import tarfile
import os
from os.path import join
import shutil
import subprocess
from argparse import ArgumentParser

import mx
import mx_subst
import mx_sdk
import re

_suite = mx.suite('mj')

def getClasspathOptions(extra_dists=None):
    """gets the classpath of the Sulong distributions"""
    return mx.get_runtime_jvm_args((extra_dists or []))


def runToyMain(args=None, out=None, get_classpath_options=getClasspathOptions):
    dists = ["MJRuntime"]
    return mx.run_java(get_classpath_options(dists) + ["-XX:+EnableJVMCI","-XX:+UseJVMCICompiler"] + args+ ["ukr.lpu.cs.mj.MJRuntime"], out=out,jdk=mx.get_jdk())


def runPESample(args=None, out=None, get_classpath_options=getClasspathOptions):
    dists = ["MJRuntime"]
    return mx.run_java(get_classpath_options(dists) + ["-XX:+EnableJVMCI","-XX:+UseJVMCICompiler","-Dgraal.TraceTruffleCompilation=true","-Dgraal.TruffleCompileImmediately=true","-Dgraal.TruffleBackgroundCompilation=false"] + args+ ["ukr.lpu.cs.mj.pesamples.SimplestPEA"], out=out,jdk=mx.get_jdk())


mx.update_commands(_suite, {
    'mj' : [runToyMain, ''],
    'peSample' : [runPESample, '']
})

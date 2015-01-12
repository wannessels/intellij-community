/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.completion

import org.jetbrains.kotlin.completion.util.*
import org.jetbrains.kotlin.idea.KotlinCompletionTestCase
import org.jetbrains.kotlin.idea.PluginTestCaseBase
import org.jetbrains.kotlin.idea.project.TargetPlatform
import org.jetbrains.kotlin.idea.stubs.AstAccessControl

public abstract class AbstractMultiFileJvmBasicCompletionTest : KotlinCompletionTestCase() {
    protected fun doTest(testPath: String) {
        configureByFile(getTestName(false) + ".kt", "")
        val shouldFail = testPath.contains("NoSpecifiedType")
        AstAccessControl.testWithControlledAccessToAst(shouldFail, getFile().getVirtualFile(), getProject(), getTestRootDisposable(), {
            testCompletion(getFile().getText(), TargetPlatform.JVM, { invocationCount ->
                complete(invocationCount)
                myItems
            }, 0)
        })
    }

    override fun getTestDataPath(): String {
        return PluginTestCaseBase.getTestDataPathBase() + "/completion/basic/multifile/" + getTestName(false) + "/"
    }
}

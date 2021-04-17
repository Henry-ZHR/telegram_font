package moe.henry_zhr.telegram_font

import android.content.res.AssetManager
import android.graphics.Typeface
import androidx.annotation.Keep
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

@Keep
class MainHook : IXposedHookLoadPackage {
  override fun handleLoadPackage(lpparam: LoadPackageParam) {
    XposedHelpers.findAndHookConstructor(
      "android.graphics.Typeface.Builder",
      lpparam.classLoader,
      AssetManager::class.java,
      String::class.java,
      object : XC_MethodHook() {
        override fun beforeHookedMethod(param: MethodHookParam) {
          if (param.args[1] in arrayOf(
              "fonts/rmediumitalic.ttf",
              "fonts/rmedium.ttf",
              "fonts/ritalic.ttf"
            )
          )
            param.result = Typeface.Builder("/system/fonts/EmptyFont.ttf")
        }
      })
  }
}
package zoho.asap.desk.sdk.zoho_desk_sdk.zoho_desk_sdk

import androidx.annotation.NonNull
import com.zoho.desk.asap.api.ZohoDeskPortalSDK
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** ZohoDeskSdkPlugin */
class ZohoDeskSdkPlugin: FlutterPlugin, MethodCallHandler {
  private lateinit var context: Context
  private lateinit var activity: Activity
  private lateinit var apiProvider: ZohoDeskPortalSDK
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "zoho_desk_sdk")
    channel.setMethodCallHandler(this)
    context = flutterPluginBinding.applicationContext

    ZohoDeskPortalSDK.Logger.enableLogs();
    apiProvider = ZohoDeskPortalSDK.getInstance(getApplicationContext());
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "initialize") {
      val map = call.arguments as HashMap<String, Any>
      apiProvider.initDesk(map["orgId"].toString(),map["appId"].toString,map["datacenterValue"].toString());
      result.success("Android Crisp sdk initialized successful");
    } else if (call.method == 'showDashBoard'){
      ZDPortalHome.show(MainActivity.this);
      result.success("Show Dashboard successful");
    } else if (call.method == 'showChat'){
      val chatUser = ZDPortalChatUser()
      val map = call.arguments as HashMap<String, Any>
      chatUser.setName(map["name"].toString())
      chatUser.setEmail(map["email"].toString())
      chatUser.setPhone(map["phone"].toString())
      ZDPortalChat.setGuestUserDetails(chatUser)
      ZDPortalChat.show(activity);
      result.success("Show Dashboard successful");
    } else if (call.method == "showKnwoledgeBase"){
      ZDPortalKB.show(activity);
      result.success("Show Knwoledge Base successful");
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    activity = binding.activity;
  }

  override fun onDetachedFromActivityForConfigChanges() {

  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {

  }

  override fun onDetachedFromActivity() {

  }
}

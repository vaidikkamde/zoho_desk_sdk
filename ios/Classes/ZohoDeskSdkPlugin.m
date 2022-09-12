#import "ZohoDeskSdkPlugin.h"
#if __has_include(<zoho_desk_sdk/zoho_desk_sdk-Swift.h>)
#import <zoho_desk_sdk/zoho_desk_sdk-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "zoho_desk_sdk-Swift.h"
#endif

@implementation ZohoDeskSdkPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftZohoDeskSdkPlugin registerWithRegistrar:registrar];
}
@end

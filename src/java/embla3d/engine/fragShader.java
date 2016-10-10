
// This file is generated from `fragShader.fs` shader program
// Please do not edit directly
package embla3d.engine;

public class fragShader {
    public final static String SHADER_STRING = "#version 330\n\nconst int MAX_POINT_LIGHTS = 5;\nconst int MAX_SPOT_LIGHTS = 5;\n\nin vec3 vNorm;\nin vec3 vPos;\n\nout vec4 fragColor;\n\nstruct PointLight\n{\n    vec3 colour;\n    // Light position is assumed to be in view coordinates\n    vec3 position;\n    float intensity;\n    \n    //Attenuation\n    float att_constant;\n    float att_linear;\n    float att_exponent;\n};\n\nstruct SpotLight\n{\n    PointLight pl;\n    vec3 conedir;\n    float cutoff;\n};\n\nstruct DirectionalLight\n{\n    vec3 colour;\n    vec3 direction;\n    float intensity;\n};\n\nstruct Material\n{\n    vec3 color;\n    float reflectance;\n};\n\nuniform vec3 ambientLight;\nuniform float specularPower;\nuniform Material material;\nuniform PointLight pointLights[MAX_POINT_LIGHTS];\nuniform SpotLight spotLights[MAX_SPOT_LIGHTS];\nuniform DirectionalLight directionalLight;\nuniform vec3 camera_pos;\n\nvec4 calcLightColour(vec3 light_colour, float light_intensity, vec3 position, vec3 to_light_dir, vec3 normal)\n{\n    vec4 diffuseColour = vec4(0, 0, 0, 0);\n    vec4 specColour = vec4(0, 0, 0, 0);\n\n    // Diffuse Light\n    float diffuseFactor = max(dot(normal, to_light_dir), 0.0);\n    diffuseColour = vec4(light_colour, 1.0) * light_intensity * diffuseFactor;\n\n    // Specular Light\n    vec3 camera_direction = normalize(camera_pos - position);\n    vec3 from_light_dir = -to_light_dir;\n    vec3 reflected_light = normalize(reflect(from_light_dir , normal));\n    float specularFactor = max( dot(camera_direction, reflected_light), 0.0);\n    specularFactor = pow(specularFactor, specularPower);\n    specColour = light_intensity  * specularFactor * material.reflectance * vec4(light_colour, 1.0);\n\n    return (diffuseColour + specColour);\n}\n\nvec4 calcPointLight(PointLight light, vec3 position, vec3 normal)\n{\n    vec3 light_direction = light.position - position;\n    vec3 to_light_dir  = normalize(light_direction);\n    vec4 light_colour = calcLightColour(light.colour, light.intensity, position, to_light_dir, normal);\n\n    // Apply Attenuation\n    float distance = length(light_direction);\n    float attenuationInv = light.att_constant + light.att_linear * distance +\n        light.att_exponent * distance * distance;\n    if(attenuationInv==0){\n        attenuationInv = 1;\n    }\n    return light_colour / attenuationInv;\n}\n\nvec4 calcSpotLight(SpotLight light, vec3 position, vec3 normal)\n{\n    vec3 light_direction = light.pl.position - position;\n    vec3 to_light_dir  = normalize(light_direction);\n    vec3 from_light_dir  = -to_light_dir;\n    float spot_alfa = dot(from_light_dir, normalize(light.conedir));\n    \n    vec4 colour = vec4(0, 0, 0, 0);\n    \n    if ( spot_alfa > light.cutoff ) \n    {\n        colour = calcPointLight(light.pl, position, normal);\n        colour *= (1.0 - (1.0 - spot_alfa)/(1.0 - light.cutoff));\n    }\n    return colour;    \n}\n\nvec4 calcDirectionalLight(DirectionalLight light, vec3 position, vec3 normal)\n{\n    return calcLightColour(light.colour, light.intensity, position, normalize(-light.direction), normal);\n}\n\nvoid main()\n{\n    vec4 baseColour = vec4(material.color, 1);\n    \n    vec4 totalLight = vec4(ambientLight, 1.0);\n    totalLight += calcDirectionalLight(directionalLight, vPos, vNorm);\n\n    for (int i=0; i<MAX_POINT_LIGHTS; i++)\n    {\n        if ( pointLights[i].intensity > 0 )\n        {\n            totalLight += calcPointLight(pointLights[i], vPos, vNorm); \n        }\n    }\n\n    for (int i=0; i<MAX_SPOT_LIGHTS; i++)\n    {\n        if ( spotLights[i].pl.intensity > 0 )\n        {\n            totalLight += calcSpotLight(spotLights[i], vPos, vNorm);\n        }\n    }\n    fragColor = vec4((baseColour * totalLight).xyz,1);\n}\n";
}
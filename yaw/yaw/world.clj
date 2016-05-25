(ns yaw.world
  (:import [gameEngine World][gameEngine.items MyItem][gameEngine.camera CameraManagement]
  [gameEngine.light LightManagement][gameEngine.camera Camera][gameEngine.items ItemManagement])
  (:gen-class))


(defn start-yaw
  []
  (let [world (.newInstance World)
        thread (future (.init world))]
	(.start (Thread. world))
    (atom {:world world :thread thread})
    ))


;; Skybox

(defn setSkybox [world width length height r g b]
      (.setSkybox world width length height r g b))

(defn removeSkybox [world]
     (.removeSkybox world))

;; Light Management----------------------------------------------------------------------------------

(defn setSunLight [world r g b intensity x y z]
      (LightManagement/setSunLight world r g b intensity x y z))

(defn setAmbientLight [world r g b intensity]
      (LightManagement/setAmbientLight world r g b intensity))

(defn addSpotLight [world r g b x y z intensity constantA linearAtt quadraticAtt xcone ycone zcone cutoffAngle number]
      (LightManagement/addSpotLight world r g b x y z  intensity constantA linearAtt quadraticAtt xcone ycone zcone cutoffAngle number))
      
(defn addPointLight [world r g b x y z intensity constantAtt linearAtt quadraticAtt number]
      (LightManagement/addPointLight world r g b x y z intensity constantAtt linearAtt quadraticAtt number))
      
(defn getSpotLightList [world]
      (.getSpotTable (.getSceneLight world)))
      
(defn getPointLightList [world]
      (.getPointTable (.getSceneLight world)))

(defn getIntensity [pointlight]
      (.getIntensity pointlight))
      
(defn setIntensity [pointlight intensity]
      (.setIntensity pointlight intensity))

(defn getConstantAtt [pointlight]
      (.getConstantAtt pointlight))

(defn setConstantAtt [pointlight att]
      (.setConstantAtt pointlight att))

(defn getLinearAtt [pointlight]
      (.getLinearAtt pointlight))

(defn setLinearAtt [pointlight att]
      (.setLinearAtt pointlight att))

(defn getQuadraticAtt [pointlight]
      (.getQuadraticAtt pointlight))

(defn setQuadraticAtt [pointlight att]
      (.setQuadraticAtt pointlight att))
      
(defn getConedir [spotlight]
       (let [pos (.getConedir spotlight)]
      (vec ((.x pos) (.y pos) (.z pos)))))

(defn setConedir [spotlight x y z]
      (.setConedir spotlight x y z))

(defn setConedirVector [spotlight vector]
      (.setConedir spotlight vector))

(defn getCutoffAngle [spotlight]
      (.getCutoffAngle spotlight))

(defn setCutoffAngle [spotlight float]
      (.setCutoffAngle spotlight float))

(defn setSpecularPower [world power]
      (.setSpecularPower (.getSceneLight world) power))

(defn getSpecularPower [world]
      (.getSpecularPower (.getSceneLight world)))

;; Camera Management--------------------------------------------------------------
(defn createCamera [world]
      (CameraManagement/addCamera world))

(defn addCamera [world camera]
      (CameraManagement/addCamera world camera))
      
(defn setLiveCamera [world camera]
      (CameraManagement/setLiveCamera world camera))

(defn getCamera [world numero]
      (CameraManagement/getCamera world numero))

(defn getCamerasList [world]
      (vec (.getCamerasList world)))

(defn getLiveCamera [world]
      (CameraManagement/getLiveCamera world))

(defn removeCameraNumber [world numero]
      (CameraManagement/removeCamera world numero))

(defn removeCamera [world camera]
      (CameraManagement/removeCamera world camera))

(defn setPositionCamera [camera x y z]
      (.setPosition camera (new org.joml.Vector3f x y z)))

(defn setOrientation [camera x y z]
      (.setOrientation camera x y z))

(defn setOrientationVector [camera vector]
      (.setOrientation camera vector))
      
;; Objects Management------------------------------------------------

(defn createGround [world r g b width length ]
      (ItemManagement/createGround world r g b width length))

(defn createBlock [world r g b xL yL zL scale]
      (ItemManagement/createBlock world r g b xL yL zL scale))

(defn createHalfBlock [world r g b xL yL zL scale]
      (ItemManagement/createHalfBlock world r g b xL yL zL scale))

(defn createPyramid [world r g b xL yL zL scale]
      (ItemManagement/createPyramid world r g b xL yL zL scale))
      
(defn createTetrahedron [world r g b scale]
      (ItemManagement/createRegTetrahedron world r g b scale))

(defn createOctahedron [world r g b scale]
      (ItemManagement/createRegOctahedron world r g b scale))

(defn clone [world item]
      (ItemManagement/clone world item))
      
(defn removeItem [world item]
      (ItemManagement/removeItem world item))
      
(defn getItemsList [world]
      (vec (.getItemsList(.getSceneVertex world))))
      
(defn setScale [item scale]
      (.setScale item scale))

(defn getScale [item]
      (.getScale item))

(defn getReflectance [item]
      (.getReflectance item))
      
(defn setReflectance [item refl]
      (.setReflectance item refl))

(defn getColor [item]
 (let [pos (.getColor item)]
      (vector (.x pos) (.y pos) (.z pos))))
      
(defn setColor [item r g b]
      (.setColor item r g b))
      
(defn setColorVector [item vector]
      (.setColor item vector))
      
(defn setRotation [item x y z]
      (.setRotation x y z))
      
(defn setRotationVector [item vector]
      (.setRotation item vector))
      
(defn getRotation [item]
       (let [pos (.getRotation item)]
      (vector (.x pos) (.y pos) (.z pos))))

(defn revolveAroundVector [item center degX degY degZ]
      (.revolveAround item center degX degY degZ))

(defn revolveAround [item centerX centerY centerZ degX degY degZ]
      (.revolveAround item (new org.joml.Vector3f centerX centerY centerZ) degX degY degZ))

(defn repelByVector [item center dist]
      (.repelBy item center dist))

(defn repelBy [item centerX centerY centerZ dist]
      (.repelBy item (new org.joml.Vector3f centerX centerY centerZ) dist))
      
;; Item Group -----------------------------------------------
(defn getGroupsList [world]
      (vec (.getGroupsList world)))
      
(defn removeGroup [world group]
      (ItemManagement/removeGroup world group))
      
(defn createGroup [world]
      (ItemManagement/createGroup world))
      
(defn addItem [group item]
     (.add group item))

(defn removeFromGroup [group item]
     (.remove group item))

(defn separate [group distance]
      (.separate group distance))
      
(defn multScale [group scale]
      (.multScale group scale))

(defn groupItems [group]
      (.getItems group))

;; Multiple usage [Camera, Item, Group]-----------------------------
(defn rotate [item x y z]
      (.rotate item x y z))

;; Multiple usage [Camera, Item, Light, Group]----------------------
(defn translate [item x y z]
      (.translate item x y z))
      
(defn setPosition [item x y z]
      (.setPosition item x y z))
      
(defn setPositionVector [item vector]
      (.setPosition item vector))
      
(defn getPosition [item]
      (let [pos (.getPosition item)]
      (vector (.x pos) (.y pos) (.z pos))))


;; Callback Management
(defn registerCallback [world keyString function]
      (.registerCallback (.getCallback world) keyString function))

(defn clearCallback [world keyString]
      (.clearCallback (.getCallback world) keyString))

(defn clearFunctionOfKey [world keyString function]
      (.clearFunctionOfKey (.getCallback world) keyString function))

;; Save Tools
;; Each object is converted to a Clojure vector to be saved. The vector
;; contains generally in its first position the Class of the object, which is 
;; followed by the arguments to be given to the constructor during loading.

;; Save Tools for Items
(defn meshToVector [mesh]
	"Converts Meshes into a savable vector."
	(let [material (.getMaterial mesh)
				color (.getColor material)]
		(condp  = (.getClass mesh)		
			gameEngine.meshs.BlockMesh (vector gameEngine.meshGenerator.BlockGenerator (.xLength mesh) (.yLength mesh) (.zLength mesh) (.x color) (.y color) (.z color) (.getReflectance material))
			gameEngine.meshs.HalfBlockMesh (vector gameEngine.meshGenerator.HalfBlockGenerator (.xLength mesh) (.yLength mesh) (.zLength mesh) (.x color) (.y color) (.z color) (.getReflectance material))
			gameEngine.meshs.GroundMesh (vector gameEngine.meshGenerator.GroundGenerator (.width mesh) (.length mesh) (.height mesh) (.x color) (.y color) (.z color) (.getReflectance material))
			gameEngine.meshs.PyramidMesh (vector gameEngine.meshGenerator.PyramidGenerator (.xLength mesh) (.yLength mesh) (.zLength mesh) (.x color) (.y color) (.z color) (.getReflectance material))
			gameEngine.meshs.TetrahedronMesh (vector gameEngine.meshGenerator.RegTetrahedronGenerator (.x color) (.y color) (.z color) (.getReflectance material))
			gameEngine.meshs.OctahedronMesh (vector gameEngine.meshGenerator.RegOctahedronGenerator (.x color) (.y color) (.z color) (.getReflectance material))
			gameEngine.meshs.Mesh	(vector gameEngine.meshs.Mesh (vec (.getVertices mesh)) (.x color) (.y color) (.z color) (.getReflectance material) (vec (.getNormals mesh)) (vec (.getIndices mesh)) (.getWeight mesh))
    )
  ))

(defn myItemToVector [myItem]
	"Converts a MyItem into a savable vector."
	(let [rotation (.getRotation myItem)
				translation (.getPosition myItem)]
	(vector (.getScale myItem) (.x rotation) (.y rotation) (.z rotation) (.x translation) (.y translation) (.z translation))
	))

(defn createMyItemVector [mesh itemListVec]
	"Saves all MyItem in the given List (as vector) in EDN format."
	(if (= (.length itemListVec) 1)
		(vector (meshToVector mesh) (myItemToVector (first itemListVec)))
		(conj (createMyItemVector mesh (pop itemListVec)) (myItemToVector (last itemListVec)))
	))

(defn saveMeshMap [meshMapVec]
	"Saves all items of the given meshMap (as vector) in EDN format."
	(if (= (.length meshMapVec) 0)
		(vector)
		(if (= (.length meshMapVec) 1)
			(vector (createMyItemVector (.getKey (first meshMapVec)) (vec (.getValue (first meshMapVec)))))
			(conj (saveMeshMap (pop meshMapVec)) (createMyItemVector (.getKey (last meshMapVec)) (vec (.getValue (last meshMapVec))))))
	))

;; Save Tools for Groups
(defn createGroupVector [groupItemsListVec allItemsArrayList]
	"Converts an ItemGroup into a savable vector."
	(if (= (.length groupItemsListVec) 0)
		(vector)
		(if (= (.length groupItemsListVec) 1)
			(vector (.indexOf allItemsArrayList (first groupItemsListVec)))
			(conj (createGroupVector (pop groupItemsListVec) allItemsArrayList) (.indexOf allItemsArrayList (last groupItemsListVec))))
	))

(defn saveGroupsList [groupsListVec allItemsArrayList]
	"Saves all groups of the given groupsList (as vector) in EDN format."
	(if (= (.length groupsListVec) 0)
		(vector)
		(if (= (.length groupsListVec) 1)
			(vector (createGroupVector (vec (.getItems (first groupsListVec))) allItemsArrayList))
			(conj (saveGroupsList (pop groupsListVec) allItemsArrayList) (createGroupVector (vec (.getItems (last groupsListVec))) allItemsArrayList)))
	))

;; Save Tools for Cameras
(defn cameraToVector [cam]
	"Converts a Camera into a savable vector."
  (let [position (.getPosition cam)]
    (vector (.getClass cam) (.getFieldOfView cam) (.getzNear cam) (.getzFar cam) (.x position) (.y position) (.z position))
  ))
  
(defn createCameraVector [cameraListVec]
	"Creates a vector containing all Cameras, converted into savable vectors."
  (if (= (.length cameraListVec) 0)
    (vector)
    (conj (createCameraVector (pop cameraListVec)) (cameraToVector (last cameraListVec)))
  ))

;; Save Tools for Lights
(defn pointLightToVector [pl]
	"Converts a PointLight into a savable vector."
	(let [color (.getColor pl)
				position (.getPosition pl)]
		(vector (.getClass pl) (.x color) (.y color) (.z color) (.x position) (.y position) (.z position) (.getIntensity pl) (.getConstantAtt pl) (.getLinearAtt pl) (.getQuadraticAtt pl))
  ))
	
(defn createPointLightVector [pointLightArrVec]
	"Creates a vector containing all PointLights, converted into savable vectors."
	(if (= (.length pointLightArrVec) 1)
		(vector (pointLightToVector (first pointLightArrVec)))
		(conj (createPointLightVector (pop pointLightArrVec)) (pointLightToVector (last pointLightArrVec)))
	))

(defn spotLightToVector [sl]
	"Converts a SpotLight into a savable vector."
	(let [color (.getColor sl)
				position (.getPosition sl)
				conedir (.getConedir sl)]
		(vector (.getClass sl) (.x color) (.y color) (.z color) (.x position) (.y position) (.z position) (.getIntensity sl) (.getConstantAtt sl) (.getLinearAtt sl) (.getQuadraticAtt sl) (.x conedir) (.y conedir) (.z conedir) (.getCutoffAngle sl))
  ))

(defn createSpotLightVector [spotLightArrVec]
	"Creates a vector containing all SpotLights, converted into savable vectors."
	(if (= (.length spotLightArrVec) 1)
		(vector (spotLightToVector (first spotLightArrVec)))
		(conj (createSpotLightVector (pop spotLightArrVec)) (spotLightToVector (last spotLightArrVec)))
	))

;; Save Functions
(defn saveItems [world]
	"Returns a vector containing all items of the given world in EDN format."
	(let [meshMapVec (vec (.getMeshMap (.getSceneVertex world)))]
				(saveMeshMap meshMapVec)
		))

(defn saveGroups [world]
	"Returns a vector containing all groups of the given world in EDN format."
	(saveGroupsList (vec (.getGroupsList world)) (.getItemsList (.getSceneVertex world)))
	)

(defn saveCameras [world]
	"Returns a vector containing all cameras of the given world in EDN format."
	(vector (cameraToVector (.getCamera world)) (createCameraVector (vec (.getCamerasList world)))
	))

(defn saveLights [world]
	"Returns a vector containing all lights of the given world in EDN format."
	(let [sceneLight (.getSceneLight world)
				ambientLight (.getAmbientLight sceneLight)
				aColor (.getColor ambientLight)
				sun (.getSun sceneLight)
				sColor (.getColor sun)
				sDirection (.getDirection sun)
				;; Representation of the ambientLight as a Clojure vector
			 	ambientVector [(.getClass ambientLight) (.x aColor) (.y aColor) (.z aColor) (.getIntensity ambientLight)]
			 	;; Representation of the Sun as a Clojure vector
			 	sunVector [(.getClass sun) (.x sColor) (.y sColor) (.z sColor) (.getIntensity sun) (.x sDirection) (.y sDirection) (.z sDirection)]	
			 	;; Representation of PointLights as a Clojure vector
			 	pointLightVector (createPointLightVector (vec (.getPointTable sceneLight)))
			 	;; Representation of spotLights as a Clojure vector
			 	spotLightVector (createSpotLightVector (vec (.getSpotTable sceneLight)))
		 	]
		(vector (.specularPower sceneLight) ambientVector sunVector pointLightVector spotLightVector)
	))

(defn saveSkybox [world]
	(let [skybox (.getSkybox world)]
		(if (= skybox nil)
			(vector)
			(let [color (.color skybox)]
				(vector (.width skybox) (.length skybox) (.height skybox) (.x color) (.y color) (.z color))))
	))

(defn saveFile [filename world]
	"Saves all items, cameras and lights of the given world into a file, in EDN format."
	(let [itemsVector (saveItems world)
				groupsVector (saveGroups world)
				camerasVector (saveCameras world)
				lightsVector (saveLights world)
				skyboxVector (saveSkybox world)]
		(spit filename (with-out-str (pr (vector itemsVector groupsVector camerasVector lightsVector skyboxVector))))
	))

;; Load Tools
(defn ednToObject [ednData]
	"Loads an object previously saved in a vector."
	(if (vector? ednData)
    (let [c (resolve (first ednData))
    			a (ednToObject (rest (lazy-seq ednData)))]
        (clojure.lang.Reflector/invokeConstructor c (into-array a)))
		(if (= ednData '())
				'()
				(if (vector? (first ednData))
					(conj (ednToObject (rest ednData)) (into-array (first ednData)))
					(conj (ednToObject (rest ednData)) (first ednData))
        )
    )
  ))

;; Load Tools for Items
(defn loadMesh [mesh]
	"Loads a Mesh."
	(case (get mesh 0)
		gameEngine.meshGenerator.BlockGenerator (gameEngine.meshGenerator.BlockGenerator/generate (get mesh 1) (get mesh 2) (get mesh 3) (get mesh 4) (get mesh 5) (get mesh 6) (get mesh 7))
		gameEngine.meshGenerator.HalfBlockGenerator (gameEngine.meshGenerator.HalfBlockGenerator/generate (get mesh 1) (get mesh 2) (get mesh 3) (get mesh 4) (get mesh 5) (get mesh 6) (get mesh 7))
		gameEngine.meshGenerator.GroundGenerator (gameEngine.meshGenerator.GroundGenerator/generate (get mesh 1) (get mesh 2) (get mesh 3) (get mesh 4) (get mesh 5) (get mesh 6) (get mesh 7))
		gameEngine.meshGenerator.PyramidGenerator (gameEngine.meshGenerator.PyramidGenerator/generate (get mesh 1) (get mesh 2) (get mesh 3) (get mesh 4) (get mesh 5) (get mesh 6) (get mesh 7))
		gameEngine.meshGenerator.RegTetrahedronGenerator (gameEngine.meshGenerator.RegTetrahedronGenerator/generate (get mesh 1) (get mesh 2) (get mesh 3) (get mesh 4))
		gameEngine.meshGenerator.RegOctahedronGenerator (gameEngine.meshGenerator.RegOctahedronGenerator/generate (get mesh 1) (get mesh 2) (get mesh 3) (get mesh 4))
		gameEngine.meshs.Mesh (gameEngine.meshs.Mesh. (float-array (get mesh 1)) (get mesh 2) (get mesh 3) (get mesh 4) (get mesh 5) (float-array (get mesh 6)) (int-array (get mesh 7)) (get mesh 8))
	))
	
(defn loadGenericItems [mesh itemsList itemRefsMap world]
	"Loads GenericItems, associating them with their Mesh, and adds them to the sceneVertex."
	(let [itemVec (first itemsList)
				sceneVertex (.getSceneVertex world)]
		(if (= (.size itemsList) 1)
			 (let [javaItem (gameEngine.items.GenericItem. mesh (get itemVec 0) (get itemVec 1) (get itemVec 2) (get itemVec 3) (get itemVec 4) (get itemVec 5) (get itemVec 6))]
			 	(.add sceneVertex javaItem)
			 	(assoc itemRefsMap (.size itemRefsMap) javaItem))
			(let [javaItem (gameEngine.items.GenericItem. mesh (get itemVec 0) (get itemVec 1) (get itemVec 2) (get itemVec 3) (get itemVec 4) (get itemVec 5) (get itemVec 6))]
				(.add sceneVertex javaItem)
				(loadGenericItems mesh (rest itemsList) (assoc itemRefsMap (.size itemRefsMap) javaItem) world)))
	))

(defn loadMeshItems [meshItems itemRefsMap world]
	"Loads items sharing one Mesh."
	(let [mesh (loadMesh (first meshItems))]
		(loadGenericItems mesh (rest meshItems) itemRefsMap world)))
  
(defn loadMeshMap [meshMapList itemRefsMap world]
	"Loads all items of the given meshMap (as list)."
	(if (= (.size meshMapList) 0)
		itemRefsMap
		(let [itemRefsMap (loadMeshItems (first meshMapList) itemRefsMap world)]
			(loadMeshMap (rest meshMapList) itemRefsMap world))
	))

;; Load Tools for Groups
(defn createNGroups [n world]
	"Creates n groups in the world."
	(if (= n 0)
		nil
		(do
			(ItemManagement/createGroup world)
			(createNGroups (- n 1) world))
	))


(defn addItemsToGroup [groupItemsList groupId itemRefsMap world]
	"Adds items from the groupItemsList to the group corresponding to the given groupId."
	(cond (> (.size groupItemsList) 0)
		(let [groupsList (.getGroupsList world)]
			(addItem (.get groupsList groupId) (.get itemRefsMap (first groupItemsList)))
			(addItemsToGroup (rest groupItemsList) groupId itemRefsMap world))
	))


(defn addItemsToGroups [groupsList curId itemRefsMap world]
	"Adds items from the groupsList vector to corresponding groups."
	(cond (> (.length groupsList) 0)
		(do
			(addItemsToGroup (lazy-seq (last groupsList)) curId itemRefsMap world)
			(addItemsToGroups (pop groupsList) (- curId 1) itemRefsMap world))
	))

;; Load Tools for Cameras
(defn addCameras [cameraList world index]
	"Adds all cameras contained in the provided list to the World."
	(if (= cameraList '())
			nil
			(do
				(CameraManagement/addCamera world (ednToObject (first cameraList)))
				(addCameras (rest cameraList) world (+ index 1)))
  ))

;; Load Tools for Lights
(defn addPointLights [pointLights sceneLight index]
	"Adds all pointLights contained in the provided list to the SceneLight."
	(if (= pointLights '())
			nil
			(do
				(.setPointTable sceneLight (ednToObject (first pointLights)) index)
				(addPointLights (rest pointLights) sceneLight (+ index 1)))
  ))
  
(defn addSpotLights [spotLights sceneLight index]
	"Adds all spotLights contained in the provided list to the SceneLight."
	(if (= spotLights '())
			nil
			(do
				(.setSpotTable sceneLight (ednToObject (first spotLights)) index)
				(addSpotLights (rest spotLights) sceneLight (+ index 1)))
  ))

;; Load Functions
(defn loadItems [loadedItems itemRefsMap world]
	"Loads all MyItem objects from an EDN vector created with saveItems."
	(loadMeshMap (lazy-seq loadedItems) itemRefsMap world)
	)
	
(defn loadGroups [loadedGroups itemRefsMap world]
	"Loads all groups from an EDN vector created with saveGroups."
	(cond (> (.size loadedGroups) 0)
		(do
			(createNGroups (.size loadedGroups) world)
			(addItemsToGroups loadedGroups (- (.size loadedGroups) 1) itemRefsMap world))
	))

(defn loadCameras [loadedCameras world]
	"Loads all Camera objects from an EDN vector created with saveCameras."
	(.setCamera world (ednToObject (get loadedCameras 0)))
	(.emptyListCamera world)
	(addCameras (lazy-seq (get loadedCameras 1)) world 0)
  )
		
(defn loadLights [loadedLights world]
	"Loads all lights from an EDN vector created with saveLights."
	(let [sceneLight (.getSceneLight world)]
		(set! (.specularPower sceneLight) (get loadedLights 0))
		(.setAmbient sceneLight (ednToObject (get loadedLights 1)))
		(.setSun sceneLight (ednToObject (get loadedLights 2)))
		(addPointLights (lazy-seq (get loadedLights 3)) sceneLight 0)
		(addSpotLights (lazy-seq (get loadedLights 4)) sceneLight 0)
	))

(defn loadSkybox [skyboxVec world]
	"Loads the Skybox, if there is one."
	(if (= (.length skyboxVec) 0)
		nil
		(let [skybox (gameEngine.skybox.Skybox. (get skyboxVec 0) (get skyboxVec 1) (get skyboxVec 2) (get skyboxVec 3) (get skyboxVec 4) (get skyboxVec 5))]
			(.setSkybox world skybox))
	))

(defn loadFile [filename world]
	"Removes the world and loads the items, cameras, lights and skybox contained in the given file in a new world."
	(.close world)
	(Thread/sleep 200)
	(let [universe (start-yaw)
				world (:world @universe)
				loadedVector (read-string (slurp filename))
				itemRefsMap (loadItems (get loadedVector 0) '{} world)]
		(loadGroups (get loadedVector 1) itemRefsMap world)
		(loadCameras (get loadedVector 2) world)
		(loadLights (get loadedVector 3) world)
		(loadSkybox (get loadedVector 4) world)
		universe
	))

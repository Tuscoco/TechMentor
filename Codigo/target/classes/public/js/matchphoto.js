const url = 'https://plmg-cc-ti2-2024-2-g20-techmentor-1.onrender.com'; // Endereço do seu servidor
const video = document.getElementById('video');
const toggleCameraButton = document.getElementById('toggleCameraButton');
const captureButton = document.getElementById('captureButton');
const status = document.getElementById('status');
const labelDetected = document.getElementById('labelDetected');

let cameraStream = null;

// Toggle Camera Functionality
toggleCameraButton.addEventListener('click', async () => {
  if (!cameraStream) {
    try {
      console.log("Attempting to turn on the camera...");
      cameraStream = await navigator.mediaDevices.getUserMedia({ video: true });
      video.srcObject = cameraStream;
      video.play();
      toggleCameraButton.textContent = 'Turn Camera Off';
      console.log("Camera is now on.");
    } catch (err) {
      console.error('Failed to access the webcam:', err);
      alert('Could not access your camera. Please check your browser permissions.');
    }
  } else {
    console.log("Turning off the camera...");
    cameraStream.getTracks().forEach(track => track.stop());
    video.srcObject = null;
    cameraStream = null;
    toggleCameraButton.textContent = 'Turn Camera On';
    console.log("Camera is now off.");
  }
});

// FaceAPI Model Loading
Promise.all([
  faceapi.nets.faceRecognitionNet.loadFromUri('../models'),
  faceapi.nets.faceLandmark68Net.loadFromUri('../models'),
  faceapi.nets.ssdMobilenetv1.loadFromUri('../models')
]).then(start);

async function start() {
  const labeledFaceDescriptors = await loadLabeledImages();
  const faceMatcher = new faceapi.FaceMatcher(labeledFaceDescriptors, 0.6);
  document.body.append('Loaded');

  captureButton.addEventListener('click', async () => {
    if (!cameraStream) {
      alert('Please turn on the camera first!');
      return;
    }

    // Tira foto
    const image = await captureImage(video);

    // Detecta se tem caras
    const detections = await faceapi.detectAllFaces(image).withFaceLandmarks().withFaceDescriptors();

   
    const faceFound = detections.length > 0;
    status.textContent = faceFound ? "Face(s) detected" : "No faces detected";

    // Detecta se o zé ta la
    const results = detections.map(d => faceMatcher.findBestMatch(d.descriptor));
    const isFaceMatched = results.some(result => result.label !== "unknown");

    if (results.length > 0) {
      labelDetected.textContent = "Detected: " + results.map(result => result.toString()).join(', ');
      console.log("Match found:", isFaceMatched);
    } else {
      labelDetected.textContent = "No known faces detected";
    }

    console.log("Face matched with known label:", isFaceMatched);
  });
}

// Capture Image from Video Element
function captureImage(videoElement) {
  const canvas = document.createElement('canvas');
  canvas.width = videoElement.videoWidth;
  canvas.height = videoElement.videoHeight;

  const context = canvas.getContext('2d');
  context.drawImage(videoElement, 0, 0, canvas.width, canvas.height);

  const image = new Image();
  image.src = canvas.toDataURL('image/png');

  return new Promise(resolve => {
    image.onload = () => resolve(image);
  });
}

// Load Labeled Images for Face Matching
async function fetchPhotoMonitorData(id) {
  try {
    const response = await fetch(`${url}/mostrarfotomonitor/${id}`);
    
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    
    const data = await response.json();
    return data; // assuming the response is an array of strings
  } catch (error) {
    console.error('Fetch error:', error);
    return [];
  }
}
async function loadLabeledImages() {
  const monitorId = 1527678; // Hardcoded ID for Felipe Portes
  const label = 'Felipe Portes'; // Label for the monitor
  const descriptions = [];

  // Fetch the photos for Felipe Portes by their ID
  const imageUrls = await fetchPhotoMonitorData(monitorId);
  console.log(imageUrls);

  for (const url of imageUrls) {
      try {
          const img = await faceapi.fetchImage(url);
          const detections = await faceapi.detectSingleFace(img).withFaceLandmarks().withFaceDescriptor();

          if (detections) {
              descriptions.push(detections.descriptor);
          }
      } catch (error) {
          console.error(`Error processing image from URL: ${url}`, error);
      }
  }

  // Returning a single LabeledFaceDescriptors object for Felipe Portes
  return [
      new faceapi.LabeledFaceDescriptors(label, descriptions)
  ];
}



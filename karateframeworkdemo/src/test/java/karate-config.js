function fn() {

  var env = karate.env || 'qa';

  karate.log('Running in env:', env);

  // Read environment-specific JSON config file
  var config;
  try {
    config = karate.read('classpath:config/' + env + '.json');
  } catch (e) {
    karate.log('Invalid env, defaulting to QA');
    config = karate.read('classpath:config/qa.json');
  }

  return config;
}

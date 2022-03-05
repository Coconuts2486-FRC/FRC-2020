import json

with open("logs/Untitled-1.json", "r") as jfile:
	data = json.load(jfile)

file = open("data.java", "a")
run_name = "auto1"
file.write("\npackage frc.robot.Autonomous;\n\npublic class data {\npublic static double[][] " + run_name + " = {\n")

first_timestamp_taken = False
first_timestamp = 0

for log in data:
	try:
		log["line"]
		skip = False
		if "Auto" in log["line"]:
			if first_timestamp_taken == False:
				first_timestamp = log["timestamp"] - 0.1
				print(first_timestamp)
				first_timestamp_taken = True
	except:
		skip = True

	if skip == False:
		if "Auto" in log["line"]:
			file.write("{" + str(log["timestamp"] - first_timestamp) + ", ")
			data = log["line"]
			data = data.replace("Auto recording start:", "")
			data = data.replace("Auto recording end", "")
			file.write(data + "},\n")

file.write("{" + str(log["timestamp"] - first_timestamp + 0.0001) + ", 0, 0, 0, 0, 0, 0 }")
file.write("};\n}")
file.close()
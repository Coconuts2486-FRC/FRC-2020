import json, os

# script that retrieves data for our auto paths 

with open("logs/Untitled-1.json", "r") as jfile:
	data = json.load(jfile)

highest_number = 0
list_of_runs = os.listdir("autos")
for file in list_of_runs:
	if "data" in file:
		file = file.replace(".java", "")
		file = file.replace("data", "")
		print(file)
		if int(file) > highest_number:
			highest_number = int(file)

file = open("autos\data" + str(highest_number + 1) + ".java", "a")
file.write("\npackage frc.robot.Autonomous.autos;\n\npublic class data" + str(highest_number + 1) + " {\npublic static double[][] positions = {\n")

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